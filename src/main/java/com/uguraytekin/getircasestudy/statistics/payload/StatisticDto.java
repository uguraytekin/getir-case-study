package com.uguraytekin.getircasestudy.statistics.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticDto {
    private String month;
    private int totalOrderCount = 0;
    private int totalBookCount = 0;
    private BigDecimal totalPurchasedAmount;

    public void incrementOrder() {
        totalOrderCount++;
    }

    public void addBook(int bookCount) {
        totalBookCount += bookCount;
    }

    public void addPurchaseAmount(BigDecimal purchasedAmount) {
        totalPurchasedAmount = totalPurchasedAmount.add(purchasedAmount.setScale(2, RoundingMode.FLOOR));
    }
}