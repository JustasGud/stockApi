package com.example.stockportfolioapi.repository;

import com.example.stockportfolioapi.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository responsible for database operations related to stocks.
 * JpaRepository already provides methods like save, findAll, findById and deleteById.
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

    /**
     * Finds a stock by its unique stock symbol.
     *
     * @param symbol stock symbol, for example AAPL
     * @return stock if it exists
     */
    Optional<Stock> findBySymbol(String symbol);
}