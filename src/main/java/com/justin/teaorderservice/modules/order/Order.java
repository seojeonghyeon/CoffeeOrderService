package com.justin.teaorderservice.modules.order;


import lombok.*;

@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String userId;
    private Boolean disabled;
}
