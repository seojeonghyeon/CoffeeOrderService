package com.justin.teaorderservice.modules.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCount {

    @Id @GeneratedValue
    @Column(name = "product_count_id")
    private Long id;

    private Long productId;

    private Integer countUp;

    private LocalDate orderDate;

    @JsonIgnore
    @OneToMany(mappedBy = "productCount", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();

    public static ProductCount createTeaOrderCount(Product product, LocalDate orderDate){
        ProductCount productCount = new ProductCount();
        productCount.setCountUp(0);
        productCount.setProductId(product.getId());
        productCount.setOrderDate(orderDate);
        return productCount;
    }

    public void addTeaOrder(ProductOrder productOrder){
        productOrders.add(productOrder);
        productOrder.setProductCount(this);
    }

    public void order(Integer quantity){
        this.countUp += quantity;
    }

    public void cancel(Integer quantity){
        this.countUp -= quantity;
    }

}