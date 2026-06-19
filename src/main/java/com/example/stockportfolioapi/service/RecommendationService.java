package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.AlphaVantageTickerResponse;
import com.example.stockportfolioapi.dto.AlphaVantageTopMoversResponse;
import com.example.stockportfolioapi.dto.DailyStockRecommendation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

/**
 * Service class that gets daily stock suggestions from a third-party stock API.
 */
@Service
public class RecommendationService {

    private final RestClient restClient;
    private final String apiKey;

    /**
     * Constructor injection of API configuration.
     *
     * @param baseUrl Alpha Vantage base URL
     * @param apiKey Alpha Vantage API key
     */
    public RecommendationService(
            @Value("${alpha.vantage.base-url}") String baseUrl,
            @Value("${alpha.vantage.api-key}") String apiKey
    ) {
        this.restClient = RestClient.create(baseUrl);
        this.apiKey = apiKey;
    }

    /**
     * Gets top 5 daily stock suggestions from Alpha Vantage.
     * Result is cached because third-party API calls should not be repeated too often.
     *
     * @return list of top 5 daily stock recommendations
     */
    @Cacheable("dailyRecommendations")
    public List<DailyStockRecommendation> getDailyRecommendations() {
        AlphaVantageTopMoversResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TOP_GAINERS_LOSERS")
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .body(AlphaVantageTopMoversResponse.class);

        if (response == null || response.getTopGainers() == null) {
            return Collections.emptyList();
        }

        return response.getTopGainers()
                .stream()
                .limit(5)
                .map(this::mapToRecommendation)
                .toList();
    }

    /**
     * Converts Alpha Vantage ticker response into application response object.
     *
     * @param ticker ticker response from Alpha Vantage
     * @return daily stock recommendation
     */
    private DailyStockRecommendation mapToRecommendation(AlphaVantageTickerResponse ticker) {
        return new DailyStockRecommendation(
                ticker.getTicker(),
                ticker.getPrice(),
                ticker.getChangeAmount(),
                ticker.getChangePercentage(),
                ticker.getVolume(),
                "This stock is currently one of the top market gainers returned by the third-party market data API."
        );
    }
}