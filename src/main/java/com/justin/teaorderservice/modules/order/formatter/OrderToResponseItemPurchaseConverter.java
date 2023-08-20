package com.justin.teaorderservice.modules.order.formatter;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.order.response.ResponseItemPurchase;
import com.justin.teaorderservice.modules.tea.TeaOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderToResponseItemPurchaseConverter implements Converter<Order, ResponseItemPurchase> {
    @Override
    public ResponseItemPurchase convert(Order source) {
        List<TeaOrder> teaOrderList = source.getTeaOrderList();

        List<ResponseItemOrder> responseItemOrderList = new ArrayList<>();

        for(TeaOrder teaOrder : teaOrderList){
            ResponseItemOrder responseItemOrder = ResponseItemOrder.builder()
                    .id(teaOrder.getId())
                    .orderQuantity(teaOrder.getOrderQuantity())
                    .price(teaOrder.getPrice())
                    .teaName(teaOrder.getTeaName())
                    .quantity(teaOrder.getQuantity())
                    .build();
            responseItemOrderList.add(responseItemOrder);
        }

        ResponseItemPurchase responseItemPurchase = ResponseItemPurchase.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .itemOrderFormList(responseItemOrderList)
                .build();
        return responseItemPurchase;
    }
}
