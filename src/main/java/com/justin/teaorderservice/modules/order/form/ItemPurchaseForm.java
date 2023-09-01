package com.justin.teaorderservice.modules.order.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class ItemPurchaseForm {

    @Null
    private Long id;

    @NotBlank
    private String userId;

    @NotNull
    @Valid
    private List<ItemOrderForm> itemOrderFormList;

}
