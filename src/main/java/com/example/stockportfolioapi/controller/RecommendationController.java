package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.dto.DailyStockRecommendation;
import com.example.stockportfolioapi.service.RecommendationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST controller that exposes daily stock recommendation endpoints.
 */
@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Constructor injection of RecommendationService.
     *
     * @param recommendationService service used for daily stock recommendations
     */
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Handles GET /api/recommendations/daily request.
     *
     * @return top 5 daily stock recommendations with HATEOAS links
     */
    @GetMapping("/api/recommendations/daily")
    public ResponseEntity<CollectionModel<EntityModel<DailyStockRecommendation>>> getDailyRecommendations() {
        List<EntityModel<DailyStockRecommendation>> recommendations = recommendationService.getDailyRecommendations()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<DailyStockRecommendation>> response = CollectionModel.of(
                recommendations,
                linkTo(RecommendationController.class).slash("api/recommendations/daily").withSelfRel(),
                linkTo(StockController.class).withRel("stocks"),
                linkTo(WatchlistController.class).withRel("watchlist")
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(300, TimeUnit.SECONDS))
                .body(response);
    }

    /**
     * Converts one recommendation into an EntityModel with HATEOAS links.
     *
     * @param recommendation daily stock recommendation
     * @return recommendation model with links
     */
    private EntityModel<DailyStockRecommendation> toModel(DailyStockRecommendation recommendation) {
        return EntityModel.of(
                recommendation,
                linkTo(RecommendationController.class)
                        .slash("api/recommendations/daily")
                        .withRel("dailyRecommendations"),
                linkTo(StockController.class).withRel("stocks")
        );
    }
}