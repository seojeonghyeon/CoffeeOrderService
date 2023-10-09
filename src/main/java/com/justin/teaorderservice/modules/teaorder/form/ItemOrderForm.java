package com.justin.teaorderservice.modules.teaorder.form;

import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

@Getter @Setter
@Builder @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOrderForm {

    @NotNull
    private Long id;

    @NotNull
    private String teaName;

    @NumberFormat(pattern = "###,###")
    @NotNull
    @PositiveOrZero
    @Range(min = 0, max = 1000000)
    private Integer price;

    @NotNull
    @PositiveOrZero
    @Range(min = 0)
    private Integer quantity;

    @Nullable
    @PositiveOrZero
    @Range(min = 0, max = 1000)
    private Integer orderQuantity;

    public static ItemOrderForm createItemOrderForm(TeaOrder teaOrder){
        ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                .id(teaOrder.getTea().getId())
                .orderQuantity(teaOrder.getQuantity())
                .price(teaOrder.getOrderPrice())
                .quantity(teaOrder.getQuantity())
                .teaName(teaOrder.getTea().getTeaName())
                .build();
        return itemOrderForm;
    }

}
