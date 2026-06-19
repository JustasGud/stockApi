package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StockService.
 */
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockService stockService;

    /**
     * Tests if all stocks are returned correctly.
     */
    @Test
    void getAllStocks_ShouldReturnStockList() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        Stock microsoft = new Stock(
                "MSFT",
                "Microsoft Corporation",
                "Technology",
                BigDecimal.valueOf(430.25),
                "USD"
        );

        when(stockRepository.findAll()).thenReturn(List.of(apple, microsoft));

        List<Stock> result = stockService.getAllStocks();

        assertEquals(2, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
        assertEquals("MSFT", result.get(1).getSymbol());

        verify(stockRepository, times(1)).findAll();
    }

    /**
     * Tests if a stock is returned when it exists.
     */
    @Test
    void getStockById_WhenStockExists_ShouldReturnStock() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        when(stockRepository.findById(1L)).thenReturn(Optional.of(apple));

        Stock result = stockService.getStockById(1L);

        assertEquals("AAPL", result.getSymbol());
        assertEquals("Apple Inc.", result.getCompanyName());

        verify(stockRepository, times(1)).findById(1L);
    }

    /**
     * Tests if exception is thrown when stock does not exist.
     */
    @Test
    void getStockById_WhenStockDoesNotExist_ShouldThrowException() {
        when(stockRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> stockService.getStockById(999L)
        );

        assertEquals("Stock was not found with id: 999", exception.getMessage());

        verify(stockRepository, times(1)).findById(999L);
    }

    /**
     * Tests if a stock is saved correctly.
     */
    @Test
    void createStock_ShouldSaveAndReturnStock() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        when(stockRepository.save(apple)).thenReturn(apple);

        Stock result = stockService.createStock(apple);

        assertEquals("AAPL", result.getSymbol());
        assertEquals(BigDecimal.valueOf(195.40), result.getCurrentPrice());

        verify(stockRepository, times(1)).save(apple);
    }

    /**
     * Tests if an existing stock is updated correctly.
     */
    @Test
    void updateStock_WhenStockExists_ShouldUpdateStock() {
        Stock existingStock = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        Stock updatedStock = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(200.50),
                "USD"
        );

        when(stockRepository.findById(1L)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(existingStock)).thenReturn(existingStock);

        Stock result = stockService.updateStock(1L, updatedStock);

        assertEquals(BigDecimal.valueOf(200.50), result.getCurrentPrice());
        assertEquals("AAPL", result.getSymbol());

        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).save(existingStock);
    }

    /**
     * Tests if stock deletion works correctly.
     */
    @Test
    void deleteStock_WhenStockExists_ShouldDeleteStock() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        when(stockRepository.findById(1L)).thenReturn(Optional.of(apple));

        stockService.deleteStock(1L);

        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).delete(apple);
    }
}