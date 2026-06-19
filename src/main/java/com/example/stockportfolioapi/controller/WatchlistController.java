package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.dto.WatchlistItemRequest;
import com.example.stockportfolioapi.model.WatchlistItem;
import com.example.stockportfolioapi.service.WatchlistService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * REST controller that exposes watchlist API endpoints.
 */
@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    /**
     * Constructor injection of WatchlistService.
     *
     * @param watchlistService service used for watchlist operations
     */
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    /**
     * Handles GET /api/watchlist request.
     *
     * @return all watchlist items with HATEOAS links
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<WatchlistItem>>> getAllWatchlistItems() {
        List<EntityModel<WatchlistItem>> items = watchlistService.getAllWatchlistItems()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<WatchlistItem>> response = CollectionModel.of(
                items,
                linkTo(WatchlistController.class).withSelfRel()
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(response);
    }

    /**
     * Handles GET /api/watchlist/{id} request.
     *
     * @param id watchlist item ID
     * @return selected watchlist item with HATEOAS links
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<WatchlistItem>> getWatchlistItemById(@PathVariable Long id) {
        WatchlistItem item = watchlistService.getWatchlistItemById(id);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(toModel(item));
    }

    /**
     * Handles POST /api/watchlist request.
     *
     * @param request watchlist item request
     * @return created watchlist item with HATEOAS links
     */
    @PostMapping
    public ResponseEntity<EntityModel<WatchlistItem>> addToWatchlist(
            @Valid @RequestBody WatchlistItemRequest request
    ) {
        WatchlistItem createdItem = watchlistService.addToWatchlist(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(createdItem));
    }

    /**
     * Handles DELETE /api/watchlist/{id} request.
     *
     * @param id watchlist item ID
     * @return empty response with 204 status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlistItem(@PathVariable Long id) {
        watchlistService.deleteWatchlistItem(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Converts a WatchlistItem into an EntityModel with HATEOAS links.
     *
     * @param item watchlist item entity
     * @return watchlist item model with related API links
     */
    private EntityModel<WatchlistItem> toModel(WatchlistItem item) {
        return EntityModel.of(
                item,
                linkTo(WatchlistController.class).slash(item.getId()).withSelfRel(),
                linkTo(WatchlistController.class).withRel("allWatchlistItems"),
                linkTo(WatchlistController.class).slash(item.getId()).withRel("deleteWatchlistItem"),
                linkTo(StockController.class).slash(item.getStock().getId()).withRel("stock")
        );
    }
}