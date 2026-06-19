package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.PortfolioSummaryResponse;
import com.example.stockportfolioapi.model.StockTransaction;
import com.example.stockportfolioapi.model.TransactionType;
import com.example.stockportfolioapi.repository.TransactionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class that calculates portfolio summary information from stock transactions.
 */
@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;

    /**
     * Constructor injection of TransactionRepository.
     *
     * @param transactionRepository repository used to read transaction data
     */
    public PortfolioService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Calculates portfolio summary from all stock transactions.
     *
     * @return calculated portfolio summary
     */
    @Cacheable("portfolioSummary")
    public PortfolioSummaryResponse getPortfolioSummary() {
        List<StockTransaction> transactions = transactionRepository.findAll();

        BigDecimal totalBuyAmount = BigDecimal.ZERO;
        BigDecimal totalSellAmount = BigDecimal.ZERO;
        int totalSharesOwned = 0;
        Set<Long> differentStockIds = new HashSet<>();

        for (StockTransaction transaction : transactions) {
            BigDecimal transactionAmount = transaction.getPrice()
                    .multiply(BigDecimal.valueOf(transaction.getQuantity()));

            differentStockIds.add(transaction.getStock().getId());

            if (transaction.getTransactionType() == TransactionType.BUY) {
                totalBuyAmount = totalBuyAmount.add(transactionAmount);
                totalSharesOwned += transaction.getQuantity();
            } else if (transaction.getTransactionType() == TransactionType.SELL) {
                totalSellAmount = totalSellAmount.add(transactionAmount);
                totalSharesOwned -= transaction.getQuantity();
            }
        }

        BigDecimal netInvestedAmount = totalBuyAmount.subtract(totalSellAmount);

        return new PortfolioSummaryResponse(
                totalBuyAmount,
                totalSellAmount,
                netInvestedAmount,
                totalSharesOwned,
                differentStockIds.size(),
                transactions.size()
        );
    }
}