package com.justin.teaorderservice.modules.tea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NotEnoughStockException;
import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Builder @AllArgsConstructor
public class Tea {

    @Id @GeneratedValue
    @Column(name = "tea_id")
    private Long id;
    private String teaName;
    private Integer price;
    private Integer stockQuantity;
    private String teaImage;
    private String description;
    private Boolean disabled;

    @OneToMany(mappedBy = "tea", cascade = CascadeType.ALL)
    private List<TeaCategory> teaCategories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tea")
    private List<TeaOrder> teaOrders;

    public void addTeaCategory(TeaCategory teaCategory){
        teaCategories.add(teaCategory);
        teaCategory.setTea(this);
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
