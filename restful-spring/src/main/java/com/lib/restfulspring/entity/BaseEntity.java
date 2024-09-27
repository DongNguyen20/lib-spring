package com.lib.restfulspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
public class BaseEntity {
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @Builder.Default
    String createdBy = "system";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    LocalDateTime createDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    @Builder.Default
    String updatedBy = "system";

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    LocalDateTime updateDate = LocalDateTime.now();
}
