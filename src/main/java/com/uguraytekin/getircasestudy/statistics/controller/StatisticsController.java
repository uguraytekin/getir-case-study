package com.uguraytekin.getircasestudy.statistics.controller;

import com.uguraytekin.getircasestudy.common.services.CustomerDetailsImpl;
import com.uguraytekin.getircasestudy.statistics.payload.CustomerStatisticsDto;
import com.uguraytekin.getircasestudy.statistics.service.StatisticsFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ugur Aytekin
 * @create: 19.12.2021
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsFacade statisticsFacade;

    @Operation(summary = "Gets Statistics Of a Customer By Customers Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics of Customer by Customer Id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerStatisticsDto.class)))
    })
    @GetMapping
    public ResponseEntity<CustomerStatisticsDto> getStatisticsByCustomerId() {

        CustomerDetailsImpl customer = (CustomerDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerStatisticsDto statisticsDto = statisticsFacade.getStatisticsByCustomerId(customer.getId());
        return ResponseEntity.ok(statisticsDto);
    }
}
