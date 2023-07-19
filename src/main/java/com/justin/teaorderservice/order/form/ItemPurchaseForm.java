package com.justin.teaorderservice.order.form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaseForm {

    @Null
    private String id;

    @NotBlank
    private String userId;

    @Valid
    @NotNull
    private List<ItemOrderForm> itemOrderFormList;

    public List<ItemOrderForm> getItemOrderFormList(){
        return itemOrderFormList;
    }

    public void setItemOrderFormList(List<ItemOrderForm> itemOrderFormList){
        this.itemOrderFormList = itemOrderFormList;
    }

}
