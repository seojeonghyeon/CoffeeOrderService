package me.justin.coffeeorderservice.modules.event;

import me.justin.coffeeorderservice.modules.point.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PointUpdateEvent {
    private final Point point;
}
