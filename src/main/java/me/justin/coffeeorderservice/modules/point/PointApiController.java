package me.justin.coffeeorderservice.modules.point;


import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.modules.member.CurrentMember;
import me.justin.coffeeorderservice.modules.member.Member;
import me.justin.coffeeorderservice.modules.vo.RequestAddPoint;
import me.justin.coffeeorderservice.modules.vo.ResponseAddPoint;
import me.justin.coffeeorderservice.modules.vo.ResponsePointResult;
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

import java.util.Objects;

@Tag(
        name = "Point API Controller",
        description = "Point API Controller"
)
@Slf4j
@Controller
@RequestMapping(PointApiController.ROOT)
@RequiredArgsConstructor
public class PointApiController {
    static final String ROOT = "/api/order/points";
    static final String ADD = "/add";
    static final String RESULT_DETAIL = "/{pointId}/detail";

    private final PointService pointService;


    @Operation(summary = "Point 충전 양식", description = "Point 충전 양식 전달")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(ADD)
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponseAddPoint> points(@CurrentMember Member member){
        Integer point = pointService.findPointById(member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseAddPoint.createResponseAddPoint(point, 0));
    }

    @Operation(summary = "Point 충전", description = "Point 충전")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(ADD)
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<String> addPoint(@CurrentMember Member member, final @RequestBody @Validated RequestAddPoint requestAddPoint){
        Integer point = pointService.findPointById(member.getId());
        if(validation(requestAddPoint.getPoint(), point)){
            Point savePoint = pointService.addPoint(member.getId(), requestAddPoint.getPoint(), requestAddPoint.getAddPoint());
            return ResponseEntity.status(HttpStatus.OK).body(savePoint.getId().toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorCode.NO_MATCH_INPUT_POINT.getDescription());
    }

    private boolean validation(Integer point, Integer getPoint){
        return Objects.equals(point, getPoint);
    }

    @Operation(summary = "Point 충전 결과", description = "Point 충전 결과")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseAddPoint.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(RESULT_DETAIL)
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<ResponsePointResult> pointResultDetail(@CurrentMember Member member, @PathVariable long pointId){
        Point point = pointService.findPointByMemberAndPointId(member.getId(), pointId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePointResult.createResponsePointResult(point));
    }
}
