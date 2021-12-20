package com.uguraytekin.getircasestudy.order.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderFilterRequest {

    @Schema(
            description = "Start date of the date range in yyyy-MM-dd'T'HH:mm:ss format.",
            example = "2021-12-20T09:35:37")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @Schema(
            description = "End date of the date range in yyyy-MM-dd'T'HH:mm:ss format.",
            example = "2021-12-31T19:25:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
}
