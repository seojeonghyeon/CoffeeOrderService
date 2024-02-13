package me.justin.coffeeorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import me.justin.coffeeorderservice.modules.category.Category;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseCategoryList {

    @Schema(description = "Category 리스트", nullable = false)
    private List<ResponseCategory> responseCategoryList;

    public static ResponseCategoryList createResponseCategoryList(List<Category> categoryList) {
        List<ResponseCategory> responseCategories = categoryList.stream()
                .map(ResponseCategory::createResponseCategory)
                .collect(Collectors.toList());
        return ResponseCategoryList.builder()
                .responseCategoryList(responseCategories)
                .build();

    }
}
