package me.justin.coffeeorderservice.modules.order;

import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.infra.exception.NotEnoughPointException;
import me.justin.coffeeorderservice.modules.member.CurrentMember;
import me.justin.coffeeorderservice.modules.member.Member;
import me.justin.coffeeorderservice.modules.menu.Menu;
import me.justin.coffeeorderservice.modules.menu.MenuService;
import me.justin.coffeeorderservice.modules.vo.RequestItemPurchase;
import me.justin.coffeeorderservice.modules.vo.ResponseOrder;
import me.justin.coffeeorderservice.modules.vo.ResponseProductOrder;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Order API Controller",
        description = "Order API Controller"
)
@Slf4j
@Controller
@RequestMapping(OrderApiController.ROOT)
@RequiredArgsConstructor
public class OrderApiController {
    public static final String ROOT = "/api/order/orders";
    static final String ORDER_DETAIL = "/{orderId}/detail";

    private final OrderService orderService;
    private final ProductOrderService productOrderService;
    private final MenuService menuService;

    @Operation(summary = "Tea 주문 가능 정보", description = "Tea 주문 가능 정보")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseOrder> items(@CurrentMember Member member){
        List<Menu> menus = menuService.findAll();
        List<ResponseProductOrder> responseProductOrders = menus.stream().map(ResponseProductOrder::createResponseProductOrder).toList();
        ResponseOrder responseOrder = ResponseOrder.createResponseOrder(member.getMemberName(), responseProductOrders);
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
    public ResponseEntity<ResponseOrder> orderDetail(@PathVariable long orderId, @CurrentMember Member member) {
        Order order = orderService.findByUserIdAndId(member.getId(), orderId);
        ResponseOrder responseOrder = null;

        if(order != null){
            List<ProductOrder> productOrders = productOrderService.findByOrderId(orderId);
            List<ResponseProductOrder> responseProductOrders = productOrders.stream()
                    .map(ResponseProductOrder::createResponseProductOrder)
                    .toList();
            responseOrder = ResponseOrder.createResponseOrder(orderId, member.getMemberName(), responseProductOrders);
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
    public ResponseEntity<String> addOrder(@CurrentMember Member member, final @RequestBody @Validated RequestItemPurchase requestItemPurchase){
        Order saveOrder = orderService.addOrder(member, requestItemPurchase);
        if(saveOrder.getStatus() == OrderStatus.REJECTED){
            throw new NotEnoughPointException(ErrorCode.NOT_ENOUGH_POINT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveOrder.getId().toString());
    }
}
