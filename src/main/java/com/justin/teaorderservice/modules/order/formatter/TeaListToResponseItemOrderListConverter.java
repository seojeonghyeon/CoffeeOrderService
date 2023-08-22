package com.justin.teaorderservice.modules.order.formatter;


import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.tea.Tea;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class TeaListToResponseItemOrderListConverter implements Converter<List<Tea>, List<ResponseItemOrder>> {
    @Override
    public List<ResponseItemOrder> convert(List<Tea> source) {
        List<ResponseItemOrder> responseItemOrderList = new ArrayList<>();
        for(Tea tea : source){
            ResponseItemOrder itemOrder = ResponseItemOrder.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            responseItemOrderList.add(itemOrder);
        }
        return responseItemOrderList;
    }
}
