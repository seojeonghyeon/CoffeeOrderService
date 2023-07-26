package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import org.springframework.validation.BindingResult;

public interface OrderService {
    Order findById(Long orderId);
    Order save(Order order);
    Long addViewOrder(BindingResult bindingResult, ItemPurchaseForm itemPurchaseForm);
}
