package com.justin.teaorderservice.modules.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {
    @Column(updatable = false)
    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @PrePersist
    public void prePersist(){
        createdDate = ZonedDateTime.now();
        updatedDate = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedDate = ZonedDateTime.now();
    }
}
