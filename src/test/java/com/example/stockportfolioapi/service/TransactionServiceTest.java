package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.TransactionRequest;
import com.example.stockportfolioapi.exception.ResourceNotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionService.
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private TransactionService transactionService;

    /**
     * Tests if transactions for selected stock are returned correctly.
     */
    @Test
    void getTransactionsByStockId_ShouldReturnTransactions() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        StockTransaction transaction = new StockTransaction(
                apple,
                TransactionType.BUY,
                5,
                BigDecimal.valueOf(190.00),
                LocalDate.of(2026, 6, 18)
        );

        when(stockService.getStockById(1L)).thenReturn(apple);
        when(transactionRepository.findByStockId(1L)).thenReturn(List.of(transaction));

        List<StockTransaction> result = transactionService.getTransactionsByStockId(1L);

        assertEquals(1, result.size());
        assertEquals(TransactionType.BUY, result.get(0).getTransactionType());
        assertEquals(5, result.get(0).getQuantity());

        verify(stockService, times(1)).getStockById(1L);
        verify(transactionRepository, times(1)).findByStockId(1L);
    }

    /**
     * Tests if a transaction is returned when it exists.
     */
    @Test
    void getTransactionById_WhenTransactionExists_ShouldReturnTransaction() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        StockTransaction transaction = new StockTransaction(
                apple,
                TransactionType.BUY,
                5,
                BigDecimal.valueOf(190.00),
                LocalDate.of(2026, 6, 18)
        );

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        StockTransaction result = transactionService.getTransactionById(1L);

        assertEquals(TransactionType.BUY, result.getTransactionType());
        assertEquals(BigDecimal.valueOf(190.00), result.getPrice());

        verify(transactionRepository, times(1)).findById(1L);
    }

    /**
     * Tests if exception is thrown when transaction does not exist.
     */
    @Test
    void getTransactionById_WhenTransactionDoesNotExist_ShouldThrowException() {
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.getTransactionById(999L)
        );

        assertEquals("Transaction was not found with id: 999", exception.getMessage());

        verify(transactionRepository, times(1)).findById(999L);
    }

    /**
     * Tests if a transaction is created correctly.
     */
    @Test
    void createTransaction_ShouldSaveAndReturnTransaction() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        TransactionRequest request = new TransactionRequest();
        request.setTransactionType(TransactionType.BUY);
        request.setQuantity(5);
        request.setPrice(BigDecimal.valueOf(190.00));
        request.setTransactionDate(LocalDate.of(2026, 6, 18));

        when(stockService.getStockById(1L)).thenReturn(apple);
        when(transactionRepository.save(any(StockTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StockTransaction result = transactionService.createTransaction(1L, request);

        assertEquals(TransactionType.BUY, result.getTransactionType());
        assertEquals(5, result.getQuantity());
        assertEquals(BigDecimal.valueOf(190.00), result.getPrice());
        assertEquals("AAPL", result.getStock().getSymbol());

        verify(stockService, times(1)).getStockById(1L);
        verify(transactionRepository, times(1)).save(any(StockTransaction.class));
    }

    /**
     * Tests if transaction deletion works correctly.
     */
    @Test
    void deleteTransaction_WhenTransactionExists_ShouldDeleteTransaction() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        StockTransaction transaction = new StockTransaction(
                apple,
                TransactionType.BUY,
                5,
                BigDecimal.valueOf(190.00),
                LocalDate.of(2026, 6, 18)
        );

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).delete(transaction);
    }
}