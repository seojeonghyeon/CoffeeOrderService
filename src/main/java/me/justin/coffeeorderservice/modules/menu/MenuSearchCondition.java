package me.justin.coffeeorderservice.modules.menu;

import lombok.*;

@Data
public class MenuSearchCondition {
    private String menuName;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean disabled;
}
