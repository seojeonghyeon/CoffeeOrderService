package com.justin.teaorderservice.modules.tea.dto;

public interface PopularTeaDto {
    Long getId();
    String getTeaName();
    Integer getPrice();
    Integer getStockQuantity();
    String getTeaImage();
    Integer getOrderCount();
}
