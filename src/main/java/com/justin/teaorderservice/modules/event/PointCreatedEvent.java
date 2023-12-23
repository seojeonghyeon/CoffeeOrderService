package com.justin.teaorderservice.modules.event;

import com.justin.teaorderservice.modules.point.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PointCreatedEvent {
    private final Point point;
}
