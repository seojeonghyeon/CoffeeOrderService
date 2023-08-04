package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.exception.ComplexException;
import com.justin.teaorderservice.modules.exception.ErrorCode;
import com.justin.teaorderservice.modules.order.*;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.order.response.ResponseItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseOrder;
import com.justin.teaorderservice.modules.order.response.ResponseTeaOrder;
import com.justin.teaorderservice.modules.tea.response.ResponseTea;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/order/v1/teas")
@RequiredArgsConstructor
public class TeaApiControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;

    @GetMapping
    ResponseEntity<ResponseItemPurchase> items(){
        List<Tea> teas = teaService.findAll();

        List<ResponseItemOrder> responseItemOrderList = new ArrayList<>();
        for(Tea tea : teas){
            ResponseItemOrder itemOrder = ResponseItemOrder.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            responseItemOrderList.add(itemOrder);
        }

        ResponseItemPurchase responseItemPurchase = ResponseItemPurchase.builder()
                .userId(UUID.randomUUID().toString())
                .itemOrderFormList(responseItemOrderList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseItemPurchase);
    }

    @GetMapping("/{teaId}")
    public ResponseEntity<ResponseTea> tea(@PathVariable long teaId){
        Tea tea = teaService.findById(teaId);
        ResponseTea responseTea = ResponseTea.builder()
                .id(tea.getId())
                .teaImage(tea.getTeaImage())
                .teaName(tea.getTeaName())
                .teaImage(tea.getTeaImage())
                .description(tea.getDescription())
                .price(tea.getPrice())
                .quantity(tea.getQuantity())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseTea);
    }

    @GetMapping("/{orderId}/detail")
    public ResponseEntity<ResponseOrder> orderDetail(@PathVariable long orderId){
        Order order = orderService.findById(orderId);
        List<TeaOrder> teaOrderList = order.getTeaOrderList();
        List<ResponseTeaOrder> responseTeaOrderList = new ArrayList<>();
        for(TeaOrder teaOrder : teaOrderList){
            ResponseTeaOrder responseTeaOrder = ResponseTeaOrder.builder()
                    .id(teaOrder.getId())
                    .teaName(teaOrder.getTeaName())
                    .orderQuantity(teaOrder.getOrderQuantity())
                    .quantity(teaOrder.getQuantity())
                    .price(teaOrder.getPrice())
                    .build();
            responseTeaOrderList.add(responseTeaOrder);
        }

        ResponseOrder responseOrder = ResponseOrder.builder()
                .id(order.getId())
                .teaOrderList(responseTeaOrderList)
                .userId(order.getUserId())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }

    @PostMapping
    public ResponseEntity<String> addOrder(final @RequestBody @Validated RequestItemPurchase requestItemPurchase) throws ComplexException{
        Long orderId = orderService.addApiOrder(requestItemPurchase);
        return ResponseEntity.status(HttpStatus.OK).body(orderId.toString());
    }

}
