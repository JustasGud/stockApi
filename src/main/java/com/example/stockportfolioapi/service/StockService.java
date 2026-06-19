package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.repository.StockRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that contains business logic for stock operations.
 * Controller talks to this service, and this service talks to the repository.
 */
@Service
public class StockService {

    private final StockRepository stockRepository;

    /**
     * Constructor injection is used because it is clean and testable.
     *
     * @param stockRepository repository used for stock database operations
     */
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * Returns all stocks from the database.
     * Result is cached to improve performance for repeated GET requests.
     *
     * @return list of all stocks
     */
    @Cacheable("stocks")
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Returns one stock by ID.
     * Result is cached by stock ID.
     *
     * @param id stock ID
     * @return found stock
     */
    @Cacheable(value = "stock", key = "#id")
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock was not found with id: " + id));
    }

    /**
     * Saves a new stock in the database.
     * All stock list cache is cleared because new data was added.
     *
     * @param stock stock data to save
     * @return saved stock
     */
    @CacheEvict(value = "stocks", allEntries = true)
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    /**
     * Updates an existing stock.
     * Updated stock is placed into cache and the stock list cache is cleared.
     *
     * @param id stock ID
     * @param updatedStock new stock data
     * @return updated stock
     */
    @CachePut(value = "stock", key = "#id")
    @CacheEvict(value = "stocks", allEntries = true)
    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existingStock = getStockById(id);

        existingStock.setSymbol(updatedStock.getSymbol());
        existingStock.setCompanyName(updatedStock.getCompanyName());
        existingStock.setSector(updatedStock.getSector());
        existingStock.setCurrentPrice(updatedStock.getCurrentPrice());
        existingStock.setCurrency(updatedStock.getCurrency());

        return stockRepository.save(existingStock);
    }

    /**
     * Deletes a stock by ID.
     * Stock cache and stock list cache are cleared after deletion.
     *
     * @param id stock ID
     */
    @CacheEvict(value = {"stock", "stocks"}, allEntries = true)
    public void deleteStock(Long id) {
        Stock existingStock = getStockById(id);
        stockRepository.delete(existingStock);
    }
}