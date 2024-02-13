package me.justin.coffeeorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import me.justin.coffeeorderservice.modules.category.Category;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseCategory {
    @Schema(description = "Category 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Category 부모 식별 번호", nullable = false, example = "1")
    private Long parentId;

    @Schema(description = "Category 이름", nullable = false, example = "Coffee")
    private String categoryName;

    public static ResponseCategory createResponseCategory(Category category) {
        return ResponseCategory.builder()
                .id(category.getId())
                .parentId(category.getParent().getId())
                .categoryName(category.getName())
                .build();
    }
}
