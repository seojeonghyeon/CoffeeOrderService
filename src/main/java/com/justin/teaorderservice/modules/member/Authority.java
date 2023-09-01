package com.justin.teaorderservice.modules.member;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Authority {
    private Long id;
    private String authorityName;

    public Authority(Long id, String authorityName){
        this.id = id;
        this.authorityName = authorityName;
    }
}
