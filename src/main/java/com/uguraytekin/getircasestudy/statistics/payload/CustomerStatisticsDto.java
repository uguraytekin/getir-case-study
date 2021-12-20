package com.uguraytekin.getircasestudy.statistics.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Ugur Aytekin
 * @create: 20.12.2021
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerStatisticsDto {
    private List<StatisticDto> statistics;
}
