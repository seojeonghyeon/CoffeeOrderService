package com.justin.teaorderservice.order.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemOrderForm {
    @NotNull
    @Range(min = 0, max = 1000)
    private Integer orderQuantity;
}
