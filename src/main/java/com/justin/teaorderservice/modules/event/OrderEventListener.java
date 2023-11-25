package com.justin.teaorderservice.modules.event;


import com.justin.teaorderservice.infra.config.AppProperties;
import com.justin.teaorderservice.infra.mail.EmailMessage;
import com.justin.teaorderservice.infra.mail.EmailService;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.OrderRepository;
import com.justin.teaorderservice.modules.order.OrderStatus;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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


    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent){
        String prefixSubject = "[TeaOrderService] 주문 ";
        String prefixContent = "새로운 주문 정보[";
        String suffixContent = "]가 생성 되었습니다.";
        Order order = orderRepository.findById(orderCreatedEvent.getOrder().getId()).orElse(null);
        Member member = Objects.requireNonNull(order).getMember();
        if(order.getStatus() == OrderStatus.CONFIRMED){
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_CONFIRMED + suffixContent, prefixSubject + MESSAGE_CONFIRMED);
        }else if(order.getStatus() == OrderStatus.CANCELED){
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_CANCELED + suffixContent, prefixSubject + MESSAGE_CANCELED);
        }else if(order.getStatus() == OrderStatus.REJECTED){
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_REJECTED + suffixContent, prefixSubject + MESSAGE_REJECTED);
        }else if(order.getStatus() == OrderStatus.COMPLETED){
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_COMPLETED + suffixContent, prefixSubject + MESSAGE_COMPLETED);
        }else{
            sendOrderCreatedEmail(order, member, prefixContent + MESSAGE_PENDING + suffixContent, prefixSubject + MESSAGE_PENDING);
        }
    }

    private void sendOrderCreatedEmail(Order order, Member member, String contextMessage, String emailSubject) {
        Context context = new Context();
        List<TeaOrder> teaOrders = order.getTeaOrders();

        context.setVariable("name", member.getMemberName());
        context.setVariable("totalPrice", order.getTotalPrice());
        teaOrders.forEach(teaOrder -> context.setVariable(teaOrder.getTea().getTeaName(),teaOrder.getQuantity() + "개 " + teaOrder.getOrderPrice()));
        context.setVariable("message", contextMessage);
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .subject(emailSubject)
                .to(member.getEmail())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }
}
