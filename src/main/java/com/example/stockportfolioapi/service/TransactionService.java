package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.TransactionRequest;
import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.model.StockTransaction;
import com.example.stockportfolioapi.repository.TransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that contains business logic for stock transaction operations.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockService stockService;

    /**
     * Constructor injection of required dependencies.
     *
     * @param transactionRepository repository used for transaction database operations
     * @param stockService service used to find stocks before creating transactions
     */
    public TransactionService(TransactionRepository transactionRepository, StockService stockService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
    }

    /**
     * Returns all transactions for one stock.
     *
     * @param stockId stock ID
     * @return list of transactions for selected stock
     */
    @Cacheable(value = "stockTransactions", key = "#stockId")
    public List<StockTransaction> getTransactionsByStockId(Long stockId) {
        stockService.getStockById(stockId);
        return transactionRepository.findByStockId(stockId);
    }

    /**
     * Returns one transaction by ID.
     *
     * @param id transaction ID
     * @return found transaction
     */
    @Cacheable(value = "transaction", key = "#id")
    public StockTransaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction was not found with id: " + id));
    }

    /**
     * Creates a new BUY or SELL transaction for a selected stock.
     *
     * @param stockId stock ID
     * @param request transaction data
     * @return created transaction
     */
    @CacheEvict(value = {"stockTransactions", "transaction", "portfolioSummary"}, allEntries = true)
    public StockTransaction createTransaction(Long stockId, TransactionRequest request) {
        Stock stock = stockService.getStockById(stockId);

        StockTransaction transaction = new StockTransaction();
        transaction.setStock(stock);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setQuantity(request.getQuantity());
        transaction.setPrice(request.getPrice());
        transaction.setTransactionDate(request.getTransactionDate());

        return transactionRepository.save(transaction);
    }

    /**
     * Deletes a transaction by ID.
     *
     * @param id transaction ID
     */
    @CacheEvict(value = {"stockTransactions", "transaction", "portfolioSummary"}, allEntries = true)
    public void deleteTransaction(Long id) {
        StockTransaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }
}