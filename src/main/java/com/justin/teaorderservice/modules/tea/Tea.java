package com.justin.teaorderservice.modules.tea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teas")
@Getter
@Setter
@Builder
@AllArgsConstructor
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

    public Tea(){
    }

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

}
