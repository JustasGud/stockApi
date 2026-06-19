package com.example.stockportfolioapi.dto;

/**
 * Response object used for returning one daily stock recommendation.
 */
public class DailyStockRecommendation {

    /**
     * Stock ticker symbol.
     */
    private String symbol;

    /**
     * Current stock price from third-party API.
     */
    private String price;

    /**
     * Stock price change amount.
     */
    private String changeAmount;

    /**
     * Stock price change percentage.
     */
    private String changePercentage;

    /**
     * Trading volume.
     */
    private String volume;

    /**
     * Simple explanation why this stock appears in recommendations.
     */
    private String reason;

    public DailyStockRecommendation() {
    }

    public DailyStockRecommendation(
            String symbol,
            String price,
            String changeAmount,
            String changePercentage,
            String volume,
            String reason
    ) {
        this.symbol = symbol;
        this.price = price;
        this.changeAmount = changeAmount;
        this.changePercentage = changePercentage;
        this.volume = volume;
        this.reason = reason;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public String getChangePercentage() {
        return changePercentage;
    }

    public String getVolume() {
        return volume;
    }

    public String getReason() {
        return reason;
    }
}