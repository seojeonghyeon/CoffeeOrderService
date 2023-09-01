package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import com.justin.teaorderservice.modules.order.response.ResponseItemOrder;
import com.justin.teaorderservice.modules.order.response.ResponseItemPurchase;
import com.justin.teaorderservice.modules.tea.response.ResponseTea;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
    private final ModelMapper modelMapper;

    @Operation(summary = "Tea 리스트 확인", description = "Tea 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseItemPurchase.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    ResponseEntity<ResponseItemPurchase> items(@AuthenticationPrincipal MemberAdapter memberAdapter){
        Member member = memberAdapter.getMember();
        List<Tea> teas = teaService.findAll();
        List<ResponseItemOrder> responseItemOrderList = new ArrayList<>();
        teas.forEach(tea -> responseItemOrderList.add(modelMapper.map(tea, ResponseItemOrder.class)));

        ResponseItemPurchase responseItemPurchase = ResponseItemPurchase.builder()
                .userId(member.getUserId())
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
        ResponseTea responseTea = modelMapper.map(tea, ResponseTea.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseTea);
    }



}
