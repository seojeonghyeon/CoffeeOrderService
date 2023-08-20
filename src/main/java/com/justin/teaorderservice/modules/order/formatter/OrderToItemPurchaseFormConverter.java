package com.justin.teaorderservice.modules.order.formatter;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import com.justin.teaorderservice.modules.tea.TeaOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderToItemPurchaseFormConverter implements Converter<Order, ItemPurchaseForm> {
    @Override
    public ItemPurchaseForm convert(Order source) {
        List<TeaOrder> teaOrderList = source.getTeaOrderList();
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();

        for(TeaOrder teaOrder : teaOrderList){
            ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                    .id(teaOrder.getId())
                    .orderQuantity(teaOrder.getOrderQuantity())
                    .quantity(teaOrder.getQuantity())
                    .price(teaOrder.getPrice())
                    .teaName(teaOrder.getTeaName())
                    .build();
            itemOrderFormList.add(itemOrderForm);
        }
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .itemOrderFormList(itemOrderFormList)
                .build();

        return itemPurchaseForm;
    }
}
