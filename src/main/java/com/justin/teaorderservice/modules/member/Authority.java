package com.justin.teaorderservice.modules.member;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    private String authorityName;

    public Authority(Long id, String authorityName){
        this.id = id;
        this.authorityName = authorityName;
    }
}
