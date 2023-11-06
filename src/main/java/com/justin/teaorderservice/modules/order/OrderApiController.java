package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaService;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseOrder;
import com.justin.teaorderservice.modules.teaorder.response.ResponseTeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.justin.teaorderservice.modules.order.OrderApiController.ROOT;

@Tag(
        name = "Order API Controller",
        description = "Order API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class OrderApiController {
    public static final String ROOT = "/api/order/orders";
    static final String ORDER_DETAIL = "/{orderId}/detail";

    private final OrderService orderService;
    private final TeaOrderService teaOrderService;
    private final TeaService teaService;

    @Operation(summary = "Tea 주문 가능 정보", description = "Tea 주문 가능 정보")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseOrder> items(@AuthenticationPrincipal MemberAdapter memberAdapter){
        Member member = memberAdapter.getMember();
        List<Tea> teas = teaService.findAll();
        List<ResponseTeaOrder> responseTeaOrders = teas.stream().map(ResponseTeaOrder::createResponseTeaOrder).toList();
        ResponseOrder responseOrder = ResponseOrder.createResponseOrder(member.getMemberName(), responseTeaOrders);
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);
    }

    @Operation(summary = "Tea 주문 정보", description = "Tea 주문 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
    @GetMapping(ORDER_DETAIL)
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseOrder> orderDetail(@PathVariable long orderId, @AuthenticationPrincipal MemberAdapter memberAdapter) {
        Member member = memberAdapter.getMember();
        Order order = orderService.findByUserIdAndId(member.getId(), orderId);
        ResponseOrder responseOrder = null;

        if(order != null){
            List<TeaOrder> teaOrders = teaOrderService.findByOrderId(orderId);
            List<ResponseTeaOrder> responseTeaOrders = teaOrders.stream()
                    .map(ResponseTeaOrder::createResponseTeaOrder)
                    .toList();
            responseOrder = ResponseOrder.createResponseOrder(orderId, member.getMemberName(), responseTeaOrders);
        }
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
                .toList();
        Long orderId = orderService.order(member.getId(), teaOrders.toArray(TeaOrder[]::new));
        return ResponseEntity.status(HttpStatus.OK).body(orderId.toString());
    }
}
