package com.justin.teaorderservice.modules.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.menu.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_order_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductOrderCount {

    @Id @GeneratedValue
    @Column(name = "product_count_id")
    private Long id;

    private Long menuId;

    private Integer countUp;

    private LocalDate orderDate;

    @JsonIgnore
    @OneToMany(mappedBy = "productOrderCount", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();

    public static ProductOrderCount createTeaOrderCount(Menu menu, LocalDate orderDate){
        ProductOrderCount productOrderCount = new ProductOrderCount();
        productOrderCount.setCountUp(0);
        productOrderCount.setMenuId(menu.getId());
        productOrderCount.setOrderDate(orderDate);
        return productOrderCount;
    }

    public void addTeaOrder(ProductOrder productOrder){
        productOrders.add(productOrder);
        productOrder.setProductOrderCount(this);
    }

    public void order(Integer quantity){
        this.countUp += quantity;
    }

    public void cancel(Integer quantity){
        this.countUp -= quantity;
    }

}