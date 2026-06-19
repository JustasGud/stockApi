package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.PortfolioSummaryResponse;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.model.StockTransaction;
import com.example.stockportfolioapi.model.TransactionType;
import com.example.stockportfolioapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PortfolioService.
 */
@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    /**
     * Tests portfolio summary calculation with BUY and SELL transactions.
     */
    @Test
    void getPortfolioSummary_ShouldCalculateCorrectValues() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        StockTransaction buyTransaction = new StockTransaction(
                apple,
                TransactionType.BUY,
                5,
                BigDecimal.valueOf(190.00),
                LocalDate.of(2026, 6, 18)
        );

        StockTransaction sellTransaction = new StockTransaction(
                apple,
                TransactionType.SELL,
                2,
                BigDecimal.valueOf(205.00),
                LocalDate.of(2026, 6, 19)
        );

        when(transactionRepository.findAll()).thenReturn(List.of(buyTransaction, sellTransaction));

        PortfolioSummaryResponse result = portfolioService.getPortfolioSummary();

        assertEquals(BigDecimal.valueOf(950.0), result.getTotalBuyAmount());
        assertEquals(BigDecimal.valueOf(410.0), result.getTotalSellAmount());
        assertEquals(BigDecimal.valueOf(540.0), result.getNetInvestedAmount());
        assertEquals(3, result.getTotalSharesOwned());
        assertEquals(1, result.getNumberOfDifferentStocks());
        assertEquals(2, result.getTransactionCount());

        verify(transactionRepository, times(1)).findAll();
    }

    /**
     * Tests portfolio summary calculation when there are no transactions.
     */
    @Test
    void getPortfolioSummary_WhenNoTransactions_ShouldReturnZeroValues() {
        when(transactionRepository.findAll()).thenReturn(List.of());

        PortfolioSummaryResponse result = portfolioService.getPortfolioSummary();

        assertEquals(BigDecimal.ZERO, result.getTotalBuyAmount());
        assertEquals(BigDecimal.ZERO, result.getTotalSellAmount());
        assertEquals(BigDecimal.ZERO, result.getNetInvestedAmount());
        assertEquals(0, result.getTotalSharesOwned());
        assertEquals(0, result.getNumberOfDifferentStocks());
        assertEquals(0, result.getTransactionCount());

        verify(transactionRepository, times(1)).findAll();
    }
}