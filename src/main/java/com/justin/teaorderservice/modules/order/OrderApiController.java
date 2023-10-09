package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.ResponseError;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import com.justin.teaorderservice.modules.tea.TeaService;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseOrder;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.teaorder.response.ResponseTeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/api/order/v1/orders")
@RequiredArgsConstructor
public class OrderApiController {
    private final TeaService teaService;
    private final OrderService orderService;
    private final TeaOrderService teaOrderService;

    @Operation(summary = "Tea 주문 정보", description = "Tea 주문 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
    @GetMapping("/{orderId}/detail")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseOrder> orderDetail(@PathVariable long orderId, @AuthenticationPrincipal MemberAdapter memberAdapter) throws ComplexException {
        Member member = memberAdapter.getMember();
        Order order = orderService.findByUserIdAndId(member.getId(), orderId);

        if(order == null){
            ResponseError responseError = ResponseError.builder()
                    .errorCode(ErrorCode.NO_MATCH_ORDER_ID_WITH_USER_ID)
                    .target(member.getPhoneNumber())
                    .build();
            throw new ComplexException(responseError);
        }

        List<TeaOrder> teaOrderList = teaOrderService.findByOrderId(order.getId());
        List<ResponseTeaOrder> responseTeaOrderList = new ArrayList<>();
        teaOrderList.stream().forEach(teaOrder -> {
            ResponseTeaOrder responseTeaOrder = ResponseTeaOrder.builder()
                    .id(teaOrder.getTeaId())
                    .orderQuantity(teaOrder.getQuantity())
                    .price(teaOrder.getPrice())
                    .quantity(teaOrder.getQuantity())
                    .teaName(teaOrder.getTeaName())
                    .build();
            responseTeaOrderList.add(responseTeaOrder);
        });

        ResponseOrder responseOrder = ResponseOrder.builder()
                .id(order.getId())
                .teaOrderList(responseTeaOrderList)
                .userId(order.getUserId())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }

    @Operation(summary = "Tea 주문", description = "Tea 주문 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<String> addOrder(@AuthenticationPrincipal MemberAdapter memberAdapter, final @RequestBody @Validated RequestItemPurchase requestItemPurchase){
        Member member = memberAdapter.getMember();
        List<RequestItemOrder> requestItemOrders = requestItemPurchase.getRequestItemOrderList();
        List<TeaOrder> teaOrders = requestItemOrders.stream()
                .filter(requestItemOrder -> requestItemOrder.getOrderQuantity() != null && requestItemOrder.getOrderQuantity() != 0)
                .map(requestItemOrder -> teaOrderService.teaOrder(requestItemOrder.getId(), requestItemOrder.getPrice(), requestItemOrder.getOrderQuantity()))
                .collect(Collectors.toList());
        Long orderId = orderService.order(member.getId(), teaOrders.toArray(TeaOrder[]::new));
        return ResponseEntity.status(HttpStatus.OK).body(orderId.toString());
    }
}
