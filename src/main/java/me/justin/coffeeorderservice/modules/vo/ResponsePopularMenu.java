package me.justin.coffeeorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponsePopularMenu {
    private Long id;
    private String menuName;
    private Integer price;
    private Integer stockQuantity;
    private String menuImage;
    private Integer orderCount;
}
