package com.uguraytekin.getircasestudy.customer.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: Ugur Aytekin
 * @create: 18.12.2021
 */

@Builder
@Document(collection = "roles")
@Getter
@Setter
public class Role {
    @Id
    private String id;

    private ERole name;
}
