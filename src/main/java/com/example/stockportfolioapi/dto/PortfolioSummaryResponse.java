package com.example.stockportfolioapi.dto;

import java.math.BigDecimal;

/**
 * Response object used for returning calculated portfolio summary data.
 */
public class PortfolioSummaryResponse {

    /**
     * Total value of BUY transactions.
     */
    private BigDecimal totalBuyAmount;

    /**
     * Total value of SELL transactions.
     */
    private BigDecimal totalSellAmount;

    /**
     * Net invested amount: total buy amount minus total sell amount.
     */
    private BigDecimal netInvestedAmount;

    /**
     * Total number of shares currently owned.
     * BUY transactions increase this value and SELL transactions decrease it.
     */
    private int totalSharesOwned;

    /**
     * Number of different stocks used in transactions.
     */
    private int numberOfDifferentStocks;

    /**
     * Total number of transactions in the system.
     */
    private int transactionCount;

    public PortfolioSummaryResponse() {
    }

    public PortfolioSummaryResponse(
            BigDecimal totalBuyAmount,
            BigDecimal totalSellAmount,
            BigDecimal netInvestedAmount,
            int totalSharesOwned,
            int numberOfDifferentStocks,
            int transactionCount
    ) {
        this.totalBuyAmount = totalBuyAmount;
        this.totalSellAmount = totalSellAmount;
        this.netInvestedAmount = netInvestedAmount;
        this.totalSharesOwned = totalSharesOwned;
        this.numberOfDifferentStocks = numberOfDifferentStocks;
        this.transactionCount = transactionCount;
    }

    public BigDecimal getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(BigDecimal totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public BigDecimal getTotalSellAmount() {
        return totalSellAmount;
    }

    public void setTotalSellAmount(BigDecimal totalSellAmount) {
        this.totalSellAmount = totalSellAmount;
    }

    public BigDecimal getNetInvestedAmount() {
        return netInvestedAmount;
    }

    public void setNetInvestedAmount(BigDecimal netInvestedAmount) {
        this.netInvestedAmount = netInvestedAmount;
    }

    public int getTotalSharesOwned() {
        return totalSharesOwned;
    }

    public void setTotalSharesOwned(int totalSharesOwned) {
        this.totalSharesOwned = totalSharesOwned;
    }

    public int getNumberOfDifferentStocks() {
        return numberOfDifferentStocks;
    }

    public void setNumberOfDifferentStocks(int numberOfDifferentStocks) {
        this.numberOfDifferentStocks = numberOfDifferentStocks;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
}