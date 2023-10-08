package com.justin.teaorderservice.modules.order;


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
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<TeaOrder> teaOrders = new ArrayList<>();

    private ZonedDateTime orderDate;

    public static Order createOrder(Member member, TeaOrder... teaOrders){
        Order order = new Order();
        order.setMember(member);
        for (TeaOrder teaOrder : teaOrders) {
            order.addTeaOrder(teaOrder);
        }
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(ZonedDateTime.now());
        order.deductOrder();
        return order;
    }

    public void deductOrder(){
        if(status != OrderStatus.PENDING){
            throw new IllegalStateException("이미 처리된 주문 이어서 주문 처리 불가능 합니다.");
        }
        this.setStatus(member.getPoint() - getTotalPrice() >= 0 ? OrderStatus.CONFIRMED : OrderStatus.REJECTED);
        if(status == OrderStatus.CONFIRMED){
            member.setPoint(member.getPoint() - getTotalPrice());
        } else if (status == OrderStatus.REJECTED) {
            throw new IllegalStateException("잔액이 부족 합니다.");
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
            throw new IllegalStateException("이미 완료된 주문 이어서 취소가 불가능 합니다.");
        }
        this.setStatus(OrderStatus.CANCELED);
        teaOrders.forEach(TeaOrder::cancel);
    }

}
