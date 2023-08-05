package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.exception.ComplexException;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import org.springframework.validation.BindingResult;

public interface OrderService {
    Order findById(Long orderId);
    Order save(Order order);
    Long addViewOrder(BindingResult bindingResult, ItemPurchaseForm itemPurchaseForm);
    Long addApiOrder(RequestItemPurchase requestItemPurchase) throws ComplexException;
}
