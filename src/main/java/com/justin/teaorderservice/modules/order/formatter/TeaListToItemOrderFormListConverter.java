package com.justin.teaorderservice.modules.order.formatter;

import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.tea.Tea;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class TeaListToItemOrderFormListConverter implements Converter<List<Tea>,List<ItemOrderForm>> {
    @Override
    public List<ItemOrderForm> convert(List<Tea> source) {
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        for(Tea tea : source){
            ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            itemOrderFormList.add(itemOrderForm);
        }
        return itemOrderFormList;
    }
}
