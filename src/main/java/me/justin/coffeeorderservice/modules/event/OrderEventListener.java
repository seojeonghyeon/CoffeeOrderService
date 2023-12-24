package me.justin.coffeeorderservice.modules.event;


import me.justin.coffeeorderservice.infra.config.AppProperties;
import me.justin.coffeeorderservice.infra.mail.EmailMessage;
import me.justin.coffeeorderservice.infra.mail.EmailService;
import me.justin.coffeeorderservice.modules.member.Member;
import me.justin.coffeeorderservice.modules.member.MemberRepository;
import me.justin.coffeeorderservice.modules.order.Order;
import me.justin.coffeeorderservice.modules.order.OrderRepository;
import me.justin.coffeeorderservice.modules.order.OrderStatus;
import me.justin.coffeeorderservice.modules.order.ProductOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Objects;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class OrderEventListener {
    private static final String MESSAGE_PENDING = "준비";
    private static final String MESSAGE_CONFIRMED = "확인";
    private static final String MESSAGE_CANCELED = "취소";
    private static final String MESSAGE_REJECTED = "거절";
    private static final String MESSAGE_COMPLETED = "완료";
    private static final String ROOT = "/api/order/orders";

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @TransactionalEventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        Order order = orderRepository.findById(orderCreatedEvent.getOrder().getId()).orElse(null);
        createEmailContents("[", "] 새로운 주문 정보가 생성 되었습니다.", order);
    }

    @TransactionalEventListener
    public void handleOrderUpdateEvent(OrderUpdateEvent orderUpdateEvent){
        Order order = orderRepository.findById(orderUpdateEvent.getOrder().getId()).orElse(null);
        createEmailContents("[", "] 주문 정보가 업데이트 되었습니다.", order);
    }

    private void createEmailContents(String prefixContent, String suffixContent, Order order) {
        String prefixSubject = "[TeaOrderService] 주문 ";
        prefixContent = prefixContent;
        suffixContent = suffixContent;

        Member member = Objects.requireNonNull(order).getMember();
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_CONFIRMED + suffixContent, prefixSubject + MESSAGE_CONFIRMED);
        } else if (order.getStatus() == OrderStatus.CANCELED) {
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_CANCELED + suffixContent, prefixSubject + MESSAGE_CANCELED);
        } else if (order.getStatus() == OrderStatus.REJECTED) {
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_REJECTED + suffixContent, prefixSubject + MESSAGE_REJECTED);
        } else if (order.getStatus() == OrderStatus.COMPLETED) {
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_COMPLETED + suffixContent, prefixSubject + MESSAGE_COMPLETED);
        } else {
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_PENDING + suffixContent, prefixSubject + MESSAGE_PENDING);
        }
    }

    private void sendOrderCreatedEmail(Order order, Member member, String contextMessage, String emailSubject) {
        Context context = new Context();
        List<ProductOrder> productOrders = order.getProductOrders();

        context.setVariable("name", member.getMemberName());
        context.setVariable("totalPrice", order.getTotalPrice());
        context.setVariable("restPoint", member.getPoint());
        context.setVariable("contents", createdDetailContents(productOrders));
        context.setVariable("message", contextMessage);
        context.setVariable("host", appProperties.getHost() + ROOT + "/" + order.getId() + "/detail");
        String message = templateEngine.process("mail/order-result", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .subject(emailSubject)
                .to(member.getEmail())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    private String createdDetailContents(List<ProductOrder> productOrders){
        StringBuffer contents = new StringBuffer();

        contents.append("<table class='table'>");
        contents.append("<thead>");
        contents.append("<tr>");
        contents.append("<th>상품 이름</th>");
        contents.append("<th>주문 가격</th>");
        contents.append("<th>주문 수량</th>");
        contents.append("</tr>");
        contents.append("</thead>");

        contents.append("<tbody>");
        productOrders.forEach(teaOrder -> {
            contents.append("<tr>");

            contents.append("<td>");
            contents.append(teaOrder.getMenu().getMenuName());
            contents.append("</td>");
            contents.append("<td>");
            contents.append(teaOrder.getOrderPrice());
            contents.append("</td>");
            contents.append("<td>");
            contents.append(teaOrder.getQuantity());
            contents.append("</td>");

            contents.append("</tr>");
        });
        contents.append("</tbody>");
        contents.append("</table>");

        return contents.toString();
    }
}
