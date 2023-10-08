package com.justin.teaorderservice.modules.tea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NotEnoughStockException;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teas")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @JsonIgnore
    @OneToMany(mappedBy = "tea", cascade = CascadeType.ALL)
    private List<TeaOrder> teaOrders;

    public Tea(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled) {
        this.teaName = teaName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.teaImage = teaImage;
        this.description = description;
        this.disabled = disabled;
    }

    public void addStock(Integer quantity){
        this.stockQuantity += quantity;
        if(disabled == true){
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
