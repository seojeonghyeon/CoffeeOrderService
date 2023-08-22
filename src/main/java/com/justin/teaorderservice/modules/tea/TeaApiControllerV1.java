package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import com.justin.teaorderservice.modules.order.*;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.order.response.ResponseItemPurchase;
import com.justin.teaorderservice.modules.order.response.ResponseOrder;
import com.justin.teaorderservice.modules.tea.response.ResponseTeaOrder;
import com.justin.teaorderservice.modules.tea.response.ResponseTea;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ConversionService conversionService;

    @Operation(summary = "Tea 리스트 확인", description = "Tea 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    ResponseEntity<ResponseItemPurchase> items(){
        List<Tea> teas = teaService.findAll();
        List<ResponseItemOrder> responseItemOrderList = conversionService.convert(teas, List.class);
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
        ResponseTea responseTea = conversionService.convert(tea, ResponseTea.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseTea);
    }

    @Operation(summary = "Tea 주문 정보", description = "Tea 주문 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseOrder.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseOrder.class)))
    })
    @GetMapping("/{orderId}/detail")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseOrder> orderDetail(@PathVariable long orderId, @AuthenticationPrincipal MemberAdapter memberAdapter) throws ComplexException {
        Map<String, String> errors = new HashMap<>();

        Member member = memberAdapter.getMember();
        Order order = orderService.findById(orderId);

        if(!order.getUserId().equals(member.getUserId())){
            errors.put(
                    member.getPhoneNumber(),
                    String.format(
                            ErrorCode.NoMatchOrderIdWithUserId.getDescription()
                    )
            );
        }
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        List<TeaOrder> teaOrderList = order.getTeaOrderList();
        List<ResponseTeaOrder> responseTeaOrderList = conversionService.convert(teaOrderList, List.class);

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
    public ResponseEntity<String> addOrder(@AuthenticationPrincipal MemberAdapter memberAdapter, final @RequestBody @Validated RequestItemPurchase requestItemPurchase) throws ComplexException{

        Map<String, String> errors = new HashMap<>();
        Member member = memberAdapter.getMember();

        if(!requestItemPurchase.getUserId().equals(member.getUserId())){
            errors.put(
                    member.getPhoneNumber(),
                    String.format(
                            ErrorCode.NoMatchUserID.getDescription()
                    )
            );
        }
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        Order order = conversionService.convert(requestItemPurchase, Order.class);
        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = order.getTeaOrderList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            TeaOrder teaOrder = teaOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = teaOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - teaOrder.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /**
                 * 재고가 없을 경우
                 */
                if(0 == tea.getQuantity()){
                    errors.put(
                            teaOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.NoQuantity.getDescription()
                            )
                    );
                }else if(isNoRemaining){
                    errors.put(
                            teaOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.LessQuantityThanOrderQuantity.getDescription(),
                                    tea.getQuantity(),
                                    teaOrder.getOrderQuantity()
                            )
                    );
                }

                /**
                 * 사용자의 Point가 없을 경우
                 */
                //추가 필요

            }else{
                teaOrderList.remove(i);
            }
        }
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        Order saveOrder = orderService.saveOrder(requestItemPurchase.getUserId(), teaOrderList);

        return ResponseEntity.status(HttpStatus.OK).body(saveOrder.getId().toString());
    }

}
