package com.example.stockportfolioapi.repository;

import com.example.stockportfolioapi.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository responsible for database operations related to stock transactions.
 */
public interface TransactionRepository extends JpaRepository<StockTransaction, Long> {

    /**
     * Finds all transactions for a selected stock.
     *
     * @param stockId stock ID
     * @return transactions connected to the selected stock
     */
    List<StockTransaction> findByStockId(Long stockId);
}