package me.justin.coffeeorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import me.justin.coffeeorderservice.modules.category.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseCategoryList {

    @Schema(description = "Category 리스트", nullable = false)
    private List<ResponseCategory> responseCategoryList = new ArrayList<>();

    public static ResponseCategoryList createResponseCategoryList(List<Category> categoryList) {
        Map<Long, ResponseCategory> categoryMap = new HashMap<>();
        List<ResponseCategory> responseCategories = categoryList.stream()
                .filter(category -> category.getParent() == null)
                .map(category -> {
                    ResponseCategory responseCategory = ResponseCategory.createResponseCategory(category);
                    categoryMap.put(responseCategory.getId(), responseCategory);
                    return responseCategory;
                }).collect(Collectors.toList());
        categoryList.stream().filter(category -> category.getParent() != null)
                .forEach(category -> {
                    ResponseCategory parent = categoryMap.get(category.getParent().getId());
                    parent.getChild().add(ResponseCategory.createResponseCategory(category));
                });

        return ResponseCategoryList.builder()
                .responseCategoryList(responseCategories)
                .build();

    }
}
