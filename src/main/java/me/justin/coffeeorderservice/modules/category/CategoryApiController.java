package me.justin.coffeeorderservice.modules.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.justin.coffeeorderservice.modules.vo.ResponseCategoryList;
import me.justin.coffeeorderservice.modules.vo.ResponseMenuList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static me.justin.coffeeorderservice.modules.category.CategoryApiController.ROOT;

@Tag(
        name = "Category API Controller",
        description = "Category API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class CategoryApiController {
    static final String ROOT = "/api/order/categories";

    private final CategoryService categoryService;

    @Operation(summary = "Category 전체 리스트 확인", description = "Category 전체 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseMenuList.class)))
    })
    @GetMapping
    ResponseEntity<ResponseCategoryList> items(){
        List<Category> categoryList = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseCategoryList.createResponseCategoryList(categoryList));
    }

}
