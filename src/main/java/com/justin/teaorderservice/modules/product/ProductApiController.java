package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.justin.teaorderservice.modules.product.ProductApiController.ROOT;

@Tag(
        name = "Product API Controller",
        description = "Product API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class ProductApiController {

    static final String ROOT = "/api/order/products";
    static final String PRODUCT_SEARCH = "/search";

    static final String PRODUCT_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS = "/popular/products";
    static final String PRODUCT_DETAIL = "/{productId}";

    private final ProductService productService;

    @Operation(summary = "Drink 전체 리스트 확인", description = "Drink 전체 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseProductList.class)))
    })
    @GetMapping
    ResponseEntity<ResponseProductList> items(){
        List<Product> products = productService.findAll();
        List<ResponseProductListItem> responseProductListItems = products.stream().map(ResponseProductListItem::createResponseProductListItem).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseProductList.createResponseProductList(responseProductListItems));
    }

    @Operation(summary = "Product 검색", description = "Product 검색 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseProductList.class)))
    })
    @GetMapping(PRODUCT_SEARCH)
    ResponseEntity<ResponseProductSearchList> search(ProductSearchCondition productSearchCondition, Pageable pageable){
        Page<ProductSearchDto> products = productService.search(productSearchCondition, pageable);
        List<ResponseProductSearch> responseProductSearches = products.stream().map(ResponseProductSearch::createResponseProductSearch).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseProductSearchList.createResponseProductSearchList(responseProductSearches));
    }

    @Operation(summary = "인기 메뉴 목록 조회", description = "최근 7일 간 인기 있는 메뉴 3개를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseProductList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseProductList.class)))
    })
    @GetMapping(PRODUCT_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS)
    ResponseEntity<ResponsePopularProducts> popularProducts(){
        List<PopularProductDto> popularProductDtos = productService.searchPopularProducts();
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePopularProducts.createResponsePopularProducts(popularProductDtos));
    }

    @Operation(summary = "Drink 상세 정보", description = "Drink 상세 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseProduct.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseProduct.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseProduct.class)))
    })
    @GetMapping(PRODUCT_DETAIL)
    public ResponseEntity<ResponseProduct> drink(@PathVariable long productId){
        Product product = productService.findById(productId);
        ResponseProduct responseProduct = ResponseProduct.createResponseProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(responseProduct);
    }

}
