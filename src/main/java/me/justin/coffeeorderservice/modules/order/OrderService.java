package me.justin.coffeeorderservice.modules.order;

import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.infra.exception.NoSuchOrderException;
import me.justin.coffeeorderservice.modules.event.OrderCreatedEvent;
import me.justin.coffeeorderservice.modules.event.OrderUpdateEvent;
import me.justin.coffeeorderservice.modules.member.Member;
import me.justin.coffeeorderservice.modules.member.MemberRepository;
import me.justin.coffeeorderservice.modules.vo.RequestItemOrder;
import me.justin.coffeeorderservice.modules.vo.RequestItemPurchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService{

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductOrderService productOrderService;
    private final ApplicationEventPublisher eventPublisher;


    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order findByUserIdAndId(String memberId, Long id) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        return orderRepository.findOrderByMemberAndId(findMember, id).orElse(null);
    }

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public Order addOrder(Member member, RequestItemPurchase requestItemPurchase){
        List<RequestItemOrder> requestItemOrders = requestItemPurchase.getRequestItemOrderList();
        List<ProductOrder> productOrders = requestItemOrders.stream()
                .filter(requestItemOrder -> requestItemOrder.getOrderQuantity() != null && requestItemOrder.getOrderQuantity() != 0)
                .map(requestItemOrder -> productOrderService.drinkOrder(requestItemOrder.getId(), requestItemOrder.getPrice(), requestItemOrder.getOrderQuantity()))
                .toList();
        return order(member.getId(), productOrders.toArray(ProductOrder[]::new));
    }

    @Transactional
    public Order order(String memberId, ProductOrder... productOrders) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        Order order = Order.createOrder(findMember, productOrders);
        if(order.getStatus() == OrderStatus.REJECTED){
            order.cancel();
        }
        Order saveOrder = orderRepository.save(order);
        eventPublisher.publishEvent(new OrderCreatedEvent(saveOrder));
        return saveOrder;
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NoSuchOrderException(ErrorCode.NO_SUCH_ORDER));
        order.cancel();
        eventPublisher.publishEvent(new OrderUpdateEvent(order));
    }

}
