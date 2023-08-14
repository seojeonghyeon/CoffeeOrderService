package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.order.*;
import com.justin.teaorderservice.modules.order.request.RequestItemOrder;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.order.response.ResponseItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseOrder;
import com.justin.teaorderservice.modules.order.response.ResponseTeaOrder;
import com.justin.teaorderservice.modules.tea.response.ResponseTea;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(
        name = "Tea API Controller V1",
        description = "Tea API Controller : V1"
)
@Slf4j
@Controller
@RequestMapping("/api/order/v1/teas")
@RequiredArgsConstructor
public class TeaApiControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;

    @Operation(summary = "Tea 리스트 확인", description = "Tea 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class)))
    })
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

    @Operation(summary = "Tea 상세 정보", description = "Tea 상세 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseTea.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseTea.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseTea.class)))
    })
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

    @Operation(summary = "Tea 주문 정보", description = "Tea 주문 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
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

    @Operation(summary = "Tea 주문", description = "Tea 주문 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<String> addOrder(final @RequestBody @Validated RequestItemPurchase requestItemPurchase) throws ComplexException{

        Map<String, String> errors = new HashMap<>();
        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        List<RequestItemOrder> requestItemOrderList = requestItemPurchase.getRequestItemOrderList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            RequestItemOrder requestItemOrder = requestItemOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = requestItemOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - requestItemOrder.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /**
                 * 재고가 없을 경우
                 */
                if(0 == tea.getQuantity()){
                    errors.put(
                            requestItemOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.NoQuantity.getDescription()
                            )
                    );
                }else if(isNoRemaining){
                    errors.put(
                            requestItemOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.LessQuantityThanOrderQuantity.getDescription(),
                                    tea.getQuantity(),
                                    requestItemOrder.getOrderQuantity()
                            )
                    );
                }

                /**
                 * 사용자의 Point가 없을 경우
                 */
                //추가 필요
            }
        }
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        Order saveOrder = orderService.saveOrder(requestItemPurchase.getUserId(), teaOrderList);

        return ResponseEntity.status(HttpStatus.OK).body(saveOrder.getId().toString());
    }

}
