package me.justin.coffeeorderservice.modules.order;


import me.justin.coffeeorderservice.infra.exception.AlreadyCompletedOrderException;
import me.justin.coffeeorderservice.infra.exception.AlreadyNotPendingOrderException;
import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.modules.common.BaseEntity;
import me.justin.coffeeorderservice.modules.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "orders") @Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();


    public static Order createOrder(Member member, ProductOrder... productOrders){
        Order order = new Order();
        order.setMember(member);
        order.setStatus(OrderStatus.PENDING);
        for (ProductOrder productOrder : productOrders) {
            order.addTeaOrder(productOrder);
        }
        order.deductPoint();
        return order;
    }

    public void deductPoint(){
        if(status != OrderStatus.PENDING){
            throw new AlreadyNotPendingOrderException(ErrorCode.ALREADY_NOT_PENDING_ORDER);
        }
        status = (member.getPoint() - getTotalPrice() >= 0 ? OrderStatus.CONFIRMED : OrderStatus.REJECTED);
        if(status == OrderStatus.CONFIRMED){
            member.deductPoint(getTotalPrice());
        }
    }

    public void addTeaOrder(ProductOrder productOrder){
        productOrders.add(productOrder);
        productOrder.setOrder(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public Integer getTotalPrice(){
        return productOrders.stream().mapToInt(ProductOrder::getTotalPrice).sum();
    }

    public void cancel(){
        if(status == OrderStatus.COMPLETED){
            throw new AlreadyCompletedOrderException(ErrorCode.ALREADY_COMPLETED_ORDER);
        }else if(status != OrderStatus.REJECTED){
            status = OrderStatus.CANCELED;
            member.inducePoint(getTotalPrice());
        }
        productOrders.forEach(ProductOrder::cancel);
    }

}
