package com.example.stockportfolioapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the response returned by Alpha Vantage top movers endpoint.
 */
public class AlphaVantageTopMoversResponse {

    /**
     * Last time when the market data was updated.
     */
    @JsonProperty("last_updated")
    private String lastUpdated;

    /**
     * List of top gaining stocks.
     */
    @JsonProperty("top_gainers")
    private List<AlphaVantageTickerResponse> topGainers;

    /**
     * List of top losing stocks.
     */
    @JsonProperty("top_losers")
    private List<AlphaVantageTickerResponse> topLosers;

    /**
     * List of most actively traded stocks.
     */
    @JsonProperty("most_actively_traded")
    private List<AlphaVantageTickerResponse> mostActivelyTraded;

    public AlphaVantageTopMoversResponse() {
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public List<AlphaVantageTickerResponse> getTopGainers() {
        return topGainers;
    }

    public List<AlphaVantageTickerResponse> getTopLosers() {
        return topLosers;
    }

    public List<AlphaVantageTickerResponse> getMostActivelyTraded() {
        return mostActivelyTraded;
    }
}