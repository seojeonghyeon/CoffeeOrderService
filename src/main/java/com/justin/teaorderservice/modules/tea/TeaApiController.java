package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.tea.response.ResponseTea;
import com.justin.teaorderservice.modules.tea.response.ResponseTeaList;
import com.justin.teaorderservice.modules.tea.response.ResponseTeaListItem;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.justin.teaorderservice.modules.tea.TeaApiController.ROOT;

@Tag(
        name = "Tea API Controller",
        description = "Tea API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class TeaApiController {

    static final String ROOT = "/api/order/teas";
    static final String TEA_DETAIL = "/{teaId}";

    private final TeaService teaService;

    @Operation(summary = "Tea 리스트 확인", description = "Tea 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseTeaList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseTeaList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseTeaList.class)))
    })
    @GetMapping
    ResponseEntity<ResponseTeaList> items(){
        List<Tea> teas = teaService.findAll();
        List<ResponseTeaListItem> responseTeaListItems = teas.stream().map(ResponseTeaListItem::createResponseTeaListItem).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseTeaList.createResponseTeaList(responseTeaListItems));
    }

    @Operation(summary = "Tea 상세 정보", description = "Tea 상세 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseTea.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseTea.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseTea.class)))
    })
    @GetMapping(TEA_DETAIL)
    public ResponseEntity<ResponseTea> tea(@PathVariable long teaId){
        Tea tea = teaService.findById(teaId);
        ResponseTea responseTea = ResponseTea.createResponseTea(tea);
        return ResponseEntity.status(HttpStatus.OK).body(responseTea);
    }

}
