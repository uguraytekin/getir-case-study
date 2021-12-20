package com.uguraytekin.getircasestudy.common.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createAt = LocalDateTime.now();
    @LastModifiedDate
    private LocalDateTime updateAt = LocalDateTime.now();
    @CreatedBy
    private String creator;
}
