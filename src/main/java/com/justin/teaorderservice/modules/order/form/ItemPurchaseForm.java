package com.justin.teaorderservice.modules.order.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ItemPurchaseForm {

    @Null
    private String id;

    @NotBlank
    private String userId;

    @Valid
    @NotNull
    private List<ItemOrderForm> itemOrderFormList;

}
