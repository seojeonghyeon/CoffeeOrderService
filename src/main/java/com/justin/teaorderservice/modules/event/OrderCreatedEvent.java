package com.justin.teaorderservice.modules.event;

import com.justin.teaorderservice.modules.order.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderCreatedEvent {
    private final Order order;
}
