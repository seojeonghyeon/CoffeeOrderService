package com.justin.teaorderservice.order.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrderForm {

    @NotNull
    private Long id;

    @NotNull
    private String teaName;

    @NotNull
    @Range(min = 0, max = 1000000)
    private Integer price;

    @NotNull
    @Range(min = 0)
    private Integer quantity;

    @NotNull
    @Range(min = 0, max = 1000)
    private Integer orderQuantity;

}
