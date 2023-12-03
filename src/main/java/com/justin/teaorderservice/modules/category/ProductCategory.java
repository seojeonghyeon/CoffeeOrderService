package com.justin.teaorderservice.modules.category;

import com.justin.teaorderservice.modules.product.Product;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "tea_category") @Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue
    @Column(name = "drink_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drink_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static ProductCategory createTeaCategory(Category category){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory(category);
        return productCategory;
    }
}
