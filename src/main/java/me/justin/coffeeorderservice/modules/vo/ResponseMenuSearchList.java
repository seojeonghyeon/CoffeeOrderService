package me.justin.coffeeorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseMenuSearchList {
    List<ResponseMenuSearch> responseMenuSearchList;

    public static ResponseMenuSearchList createResponseMenuSearchList(List<ResponseMenuSearch> responseMenuSearchList){
        ResponseMenuSearchList menuSearchList = ResponseMenuSearchList.builder()
                .responseMenuSearchList(responseMenuSearchList)
                .build();
        return menuSearchList;
    }
}
