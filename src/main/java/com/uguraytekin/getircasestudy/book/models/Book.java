package com.uguraytekin.getircasestudy.book.models;

import com.uguraytekin.getircasestudy.common.models.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */
@Document(collection = "books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book extends BaseEntity {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @Min(0)
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private Integer stock;

    @Version
    private Integer version;
}
