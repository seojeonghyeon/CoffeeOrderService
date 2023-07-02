package com.justin.teaorderservice.order;


import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long id;
    private String userId;
    private List<TeaOrder> teaList;

    public Order(){
    }

    public Order(String userId, List<TeaOrder> teaList) {
        this.userId = userId;
        this.teaList = teaList;
    }
}
