package com.justin.teaorderservice.modules.order.formatter;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.request.RequestItemOrder;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.tea.TeaOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RequestItemPurchaseToOrderConverter implements Converter<RequestItemPurchase, Order> {
    @Override
    public Order convert(RequestItemPurchase source) {
        List<RequestItemOrder> requestItemOrderList = source.getRequestItemOrderList();

        List<TeaOrder> teaOrderList = new ArrayList<>();
        for(RequestItemOrder requestItemOrder : requestItemOrderList){
            TeaOrder teaOrder = TeaOrder.builder()
                    .id(requestItemOrder.getId())
                    .quantity(requestItemOrder.getQuantity())
                    .orderQuantity(requestItemOrder.getOrderQuantity())
                    .price(requestItemOrder.getPrice())
                    .teaName(requestItemOrder.getTeaName())
                    .build();
            teaOrderList.add(teaOrder);
        }
        Order order = Order.builder()
                .userId(source.getUserId())
                .teaOrderList(teaOrderList).build();
        return order;
    }
}
