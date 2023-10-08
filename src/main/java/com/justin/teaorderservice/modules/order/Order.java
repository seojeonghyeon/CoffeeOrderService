package com.justin.teaorderservice.modules.order;


import com.justin.teaorderservice.infra.exception.AlreadyCompletedOrderException;
import com.justin.teaorderservice.infra.exception.AlreadyNotPendingOrderException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NotEnoughPointException;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Table(name = "orders")
@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<TeaOrder> teaOrders = new ArrayList<>();

    private ZonedDateTime orderDate;

    public static Order createOrder(Member member, TeaOrder... teaOrders){
        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.PENDING)
                .orderDate(ZonedDateTime.now())
                .build();
        for (TeaOrder teaOrder : teaOrders) {
            order.addTeaOrder(teaOrder);
        }
        order.deductPoint();
        return order;
    }

    public void deductPoint(){
        if(status != OrderStatus.PENDING){
            throw new AlreadyNotPendingOrderException(ErrorCode.ALREADY_NOT_PENDING_ORDER);
        }
        this.setStatus(member.getPoint() - getTotalPrice() >= 0 ? OrderStatus.CONFIRMED : OrderStatus.REJECTED);
        if(status == OrderStatus.CONFIRMED){
            member.setPoint(member.getPoint() - getTotalPrice());
        } else if (status == OrderStatus.REJECTED) {
            throw new NotEnoughPointException(ErrorCode.NOT_ENOUGH_POINT);
        }
    }

    public void addTeaOrder(TeaOrder teaOrder){
        teaOrders.add(teaOrder);
        teaOrder.setOrder(this);
    }

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public Integer getTotalPrice(){
        return teaOrders.stream().mapToInt(TeaOrder::getTotalPrice).sum();
    }

    public void cancel(){
        if(status == OrderStatus.COMPLETED){
            throw new AlreadyCompletedOrderException(ErrorCode.ALREADY_COMPLETED_ORDER);
        }
        this.setStatus(OrderStatus.CANCELED);
        teaOrders.forEach(TeaOrder::cancel);
    }

}
