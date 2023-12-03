package com.justin.teaorderservice.modules.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NotEnoughStockException;
import com.justin.teaorderservice.modules.category.ProductCategory;
import com.justin.teaorderservice.modules.order.ProductOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private String productName;
    private Integer price;
    private Integer stockQuantity;
    private String productImage;
    private String description;
    private Boolean disabled;

    @OneToMany(mappedBy = "product")
    private List<ProductCategory> productCategories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductOrder> productOrders;

    public void addTeaOrder(ProductOrder productOrder){
        productOrders.add(productOrder);
        productOrder.setProduct(this);
    }

    public void addDrinkCategory(ProductCategory productCategory){
        productCategories.add(productCategory);
        productCategory.setProduct(this);
    }

    public void addStock(Integer quantity){
        this.stockQuantity += quantity;
        if(disabled){
            setDisabled(false);
        }
    }

    public void removeStock(Integer quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        this.stockQuantity = restStock;
        if(this.stockQuantity == 0){
            this.disabled = true;
        }
    }

}
