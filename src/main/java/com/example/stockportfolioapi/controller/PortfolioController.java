package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.dto.PortfolioSummaryResponse;
import com.example.stockportfolioapi.service.PortfolioService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST controller that exposes portfolio summary endpoint.
 */
@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * Constructor injection of PortfolioService.
     *
     * @param portfolioService service used for portfolio calculations
     */
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    /**
     * Handles GET /api/portfolio/summary request.
     *
     * @return calculated portfolio summary with HATEOAS links
     */
    @GetMapping("/api/portfolio/summary")
    public ResponseEntity<EntityModel<PortfolioSummaryResponse>> getPortfolioSummary() {
        PortfolioSummaryResponse summary = portfolioService.getPortfolioSummary();

        EntityModel<PortfolioSummaryResponse> response = EntityModel.of(
                summary,
                linkTo(PortfolioController.class).slash("api/portfolio/summary").withSelfRel(),
                linkTo(StockController.class).withRel("stocks"),
                linkTo(WatchlistController.class).withRel("watchlist")
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(response);
    }
}