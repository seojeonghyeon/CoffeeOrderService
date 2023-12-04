package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.common.BaseEntity;
import com.justin.teaorderservice.modules.menu.Menu;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductOrder extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer orderPrice;

    private Integer quantity;

    private ProductOrderStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_count_id")
    private ProductOrderCount productOrderCount;

    public static ProductOrder createProductOrder(Menu menu, ProductOrderCount productOrderCount, Integer orderPrice, Integer quantity){
        ProductOrder productOrder = ProductOrder.builder()
                .orderPrice(orderPrice)
                .quantity(quantity)
                .status(ProductOrderStatus.PENDING)
                .build();
        menu.addProductOrder(productOrder);
        productOrderCount.addTeaOrder(productOrder);
        menu.removeStock(quantity);
        productOrderCount.order(quantity);
        productOrder.setStatus(ProductOrderStatus.CONFIRMED);
        return productOrder;
    }

    public void cancel(){
        setStatus(ProductOrderStatus.CANCELED);
        getMenu().addStock(quantity);
        productOrderCount.cancel(quantity);
    }

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
