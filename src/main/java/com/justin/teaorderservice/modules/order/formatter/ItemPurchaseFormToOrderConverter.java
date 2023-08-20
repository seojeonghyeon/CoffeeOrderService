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
public class ItemPurchaseFormToOrderConverter implements Converter<ItemPurchaseForm, Order> {
    @Override
    public Order convert(ItemPurchaseForm source) {
        List<ItemOrderForm> itemOrderFormList = source.getItemOrderFormList();

        List<TeaOrder> teaOrderList = new ArrayList<>();
        for(ItemOrderForm itemOrderForm : itemOrderFormList){
            TeaOrder teaOrder = TeaOrder.builder()
                    .id(itemOrderForm.getId())
                    .quantity(itemOrderForm.getQuantity())
                    .orderQuantity(itemOrderForm.getOrderQuantity())
                    .price(itemOrderForm.getPrice())
                    .teaName(itemOrderForm.getTeaName())
                    .build();
            teaOrderList.add(teaOrder);
        }
        Order order = Order.builder()
                .userId(source.getUserId())
                .teaOrderList(teaOrderList).build();
        return order;
    }
}
