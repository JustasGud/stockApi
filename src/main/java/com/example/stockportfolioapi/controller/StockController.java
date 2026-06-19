package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.service.StockService;
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
 * REST controller that exposes stock API endpoints.
 * This controller returns HATEOAS links and cacheable GET responses.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    /**
     * Constructor injection of StockService.
     *
     * @param stockService service used for stock operations
     */
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Handles GET /api/stocks request.
     * Response includes cache headers.
     *
     * @return all stocks with HATEOAS links
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Stock>>> getAllStocks() {
        List<EntityModel<Stock>> stocks = stockService.getAllStocks()
                .stream()
                .map(this::toModel)
                .toList();

        CollectionModel<EntityModel<Stock>> response = CollectionModel.of(
                stocks,
                linkTo(StockController.class).withSelfRel()
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(response);
    }

    /**
     * Handles GET /api/stocks/{id} request.
     * Response includes cache headers.
     *
     * @param id stock ID
     * @return selected stock with HATEOAS links
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Stock>> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(toModel(stock));
    }

    /**
     * Handles POST /api/stocks request.
     *
     * @param stock stock data from request body
     * @return created stock with HATEOAS links
     */
    @PostMapping
    public ResponseEntity<EntityModel<Stock>> createStock(@Valid @RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toModel(createdStock));
    }

    /**
     * Handles PUT /api/stocks/{id} request.
     *
     * @param id stock ID
     * @param stock updated stock data
     * @return updated stock with HATEOAS links
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Stock>> updateStock(@PathVariable Long id, @Valid @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(id, stock);

        return ResponseEntity.ok(toModel(updatedStock));
    }

    /**
     * Handles DELETE /api/stocks/{id} request.
     *
     * @param id stock ID
     * @return empty response with 204 status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Converts a Stock object into an EntityModel with HATEOAS links.
     *
     * @param stock stock entity
     * @return stock model with related API links
     */
    private EntityModel<Stock> toModel(Stock stock) {
        return EntityModel.of(
                stock,
                linkTo(StockController.class).slash(stock.getId()).withSelfRel(),
                linkTo(StockController.class).withRel("allStocks"),
                linkTo(StockController.class).slash(stock.getId()).withRel("updateStock"),
                linkTo(StockController.class).slash(stock.getId()).withRel("deleteStock")
        );
    }
}