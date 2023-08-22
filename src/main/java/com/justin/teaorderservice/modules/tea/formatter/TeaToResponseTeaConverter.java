package com.justin.teaorderservice.modules.tea.formatter;

import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.response.ResponseTea;
import org.springframework.core.convert.converter.Converter;

public class TeaToResponseTeaConverter implements Converter<Tea, ResponseTea> {
    @Override
    public ResponseTea convert(Tea source) {
        ResponseTea responseTea = ResponseTea.builder()
                .id(source.getId())
                .teaImage(source.getTeaImage())
                .teaName(source.getTeaName())
                .teaImage(source.getTeaImage())
                .description(source.getDescription())
                .price(source.getPrice())
                .quantity(source.getQuantity())
                .build();
        return responseTea;
    }
}
