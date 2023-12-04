package com.justin.teaorderservice.modules.menu;

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

import static com.justin.teaorderservice.modules.menu.MenuApiController.ROOT;

@Tag(
        name = "Menu API Controller",
        description = "Menu API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class MenuApiController {

    static final String ROOT = "/api/order/menus";
    static final String MENU_SEARCH = "/search";

    static final String MENU_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS = "/popular/menus";
    static final String MENU_DETAIL = "/{menuId}";

    private final MenuService menuService;

    @Operation(summary = "Menu 전체 리스트 확인", description = "Menu 전체 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseMenuList.class)))
    })
    @GetMapping
    ResponseEntity<ResponseMenuList> items(){
        List<Menu> menus = menuService.findAll();
        List<ResponseMenuListItem> responseMenuListItems = menus.stream().map(ResponseMenuListItem::createResponseMenuListItem).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMenuList.createResponseMenuList(responseMenuListItems));
    }

    @Operation(summary = "Menu 검색", description = "Menu 검색 리스트 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseMenuList.class)))
    })
    @GetMapping(MENU_SEARCH)
    ResponseEntity<ResponseMenuSearchList> search(MenuSearchCondition menuSearchCondition, Pageable pageable){
        Page<MenuSearchDto> menus = menuService.search(menuSearchCondition, pageable);
        List<ResponseMenuSearch> responseMenuSearches = menus.stream().map(ResponseMenuSearch::createResponseMenuSearch).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMenuSearchList.createResponseMenuSearchList(responseMenuSearches));
    }

    @Operation(summary = "인기 메뉴 목록 조회", description = "최근 7일 간 인기 있는 메뉴 3개를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseMenuList.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseMenuList.class)))
    })
    @GetMapping(MENU_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS)
    ResponseEntity<ResponsePopularMenus> popularMenus(){
        List<PopularMenuDto> popularMenuDtos = menuService.searchPopularMenus();
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePopularMenus.createResponsePopularMenus(popularMenuDtos));
    }

    @Operation(summary = "Menu 상세 정보", description = "Menu 상세 정보 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = ResponseMenu.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = ResponseMenu.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = ResponseMenu.class)))
    })
    @GetMapping(MENU_DETAIL)
    public ResponseEntity<ResponseMenu> drink(@PathVariable long menuId){
        Menu menu = menuService.findById(menuId);
        ResponseMenu responseMenu = ResponseMenu.createResponseMenu(menu);
        return ResponseEntity.status(HttpStatus.OK).body(responseMenu);
    }

}
