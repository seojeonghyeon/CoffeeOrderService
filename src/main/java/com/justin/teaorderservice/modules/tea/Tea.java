package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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
    private Integer quantity;
    private String teaImage;
    private String description;
    private Boolean disabled;

    public Tea(){
    }

    public Tea(String teaName, Integer price, Integer quantity, String teaImage, String description, boolean disabled) {
        this.teaName = teaName;
        this.price = price;
        this.quantity = quantity;
        this.teaImage = teaImage;
        this.description = description;
        this.disabled = disabled;
    }
}
