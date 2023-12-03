package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.common.BaseEntity;
import com.justin.teaorderservice.modules.product.Product;
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
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer orderPrice;

    private Integer quantity;

    private Boolean disabled;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_count_id")
    private ProductCount productCount;

    public static ProductOrder createProductOrder(Product product, ProductCount productCount, Integer orderPrice, Integer quantity){
        ProductOrder productOrder = ProductOrder.builder()
                .orderPrice(orderPrice)
                .quantity(quantity)
                .disabled(false)
                .build();
        product.addTeaOrder(productOrder);
        productCount.addTeaOrder(productOrder);
        product.removeStock(quantity);
        productCount.order(quantity);
        return productOrder;
    }

    public void cancel(){
        setDisabled(true);
        getProduct().addStock(quantity);
        productCount.cancel(quantity);
    }

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
