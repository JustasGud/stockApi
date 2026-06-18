package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.repository.StockRepository;
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
     *
     * @return list of all stocks
     */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /**
     * Returns one stock by ID.
     *
     * @param id stock ID
     * @return found stock
     */
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock was not found with id: " + id));
    }

    /**
     * Saves a new stock in the database.
     *
     * @param stock stock data to save
     * @return saved stock
     */
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }
}