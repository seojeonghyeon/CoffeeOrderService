package com.justin.teaorderservice.order.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ItemPurchaseForm {

    @NotBlank
    private String userId;

    @NotBlank
    private List<ItemOrderForm> teaOrderList;

}
