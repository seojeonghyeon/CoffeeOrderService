package me.justin.coffeeorderservice.modules.event;


import me.justin.coffeeorderservice.modules.order.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderUpdateEvent {
    private final Order order;
}
