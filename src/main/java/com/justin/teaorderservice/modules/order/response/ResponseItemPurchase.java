package com.justin.teaorderservice.modules.order.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseItemPurchase {
    private String id;
    private String userId;
    private List<ResponseItemOrder> itemOrderFormList;
}
