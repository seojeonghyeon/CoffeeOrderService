package me.justin.coffeeorderservice.modules.menu;

public interface PopularMenuDto {
    Long getId();
    String getMenuName();
    Integer getPrice();
    Integer getStockQuantity();
    String getMenuImage();
    Integer getOrderCount();
}
