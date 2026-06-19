package com.example.stockportfolioapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents one ticker object returned by Alpha Vantage top movers endpoint.
 */
public class AlphaVantageTickerResponse {

    /**
     * Stock ticker symbol.
     */
    private String ticker;

    /**
     * Current stock price.
     */
    private String price;

    /**
     * Price change amount.
     */
    @JsonProperty("change_amount")
    private String changeAmount;

    /**
     * Price change percentage.
     */
    @JsonProperty("change_percentage")
    private String changePercentage;

    /**
     * Trading volume.
     */
    private String volume;

    public AlphaVantageTickerResponse() {
    }

    public String getTicker() {
        return ticker;
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
}