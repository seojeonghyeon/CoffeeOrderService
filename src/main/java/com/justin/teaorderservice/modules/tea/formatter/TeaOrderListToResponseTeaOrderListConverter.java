package com.justin.teaorderservice.modules.tea.formatter;

import com.justin.teaorderservice.modules.tea.TeaOrder;
import com.justin.teaorderservice.modules.tea.response.ResponseTeaOrder;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class TeaOrderListToResponseTeaOrderListConverter implements Converter<List<TeaOrder>, List<ResponseTeaOrder>> {
    @Override
    public List<ResponseTeaOrder> convert(List<TeaOrder> source) {
        List<ResponseTeaOrder> responseTeaOrderList = new ArrayList<>();
        for(TeaOrder teaOrder : source){
            ResponseTeaOrder responseTeaOrder = ResponseTeaOrder.builder()
                    .id(teaOrder.getId())
                    .teaName(teaOrder.getTeaName())
                    .orderQuantity(teaOrder.getOrderQuantity())
                    .quantity(teaOrder.getQuantity())
                    .price(teaOrder.getPrice())
                    .build();
            responseTeaOrderList.add(responseTeaOrder);
        }
        return responseTeaOrderList;
    }
}
