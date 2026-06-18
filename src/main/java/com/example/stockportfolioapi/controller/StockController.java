package com.example.stockportfolioapi.controller;

import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes stock API endpoints.
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
     *
     * @return all stocks
     */
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    /**
     * Handles GET /api/stocks/{id} request.
     *
     * @param id stock ID
     * @return selected stock
     */
    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    /**
     * Handles POST /api/stocks request.
     *
     * @param stock stock data from request body
     * @return created stock
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock createStock(@Valid @RequestBody Stock stock) {
        return stockService.createStock(stock);
    }
}