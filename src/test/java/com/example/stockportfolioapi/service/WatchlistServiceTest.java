package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.WatchlistItemRequest;
import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.model.WatchlistItem;
import com.example.stockportfolioapi.repository.WatchlistRepository;
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
 * Unit tests for WatchlistService.
 */
@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private WatchlistService watchlistService;

    /**
     * Tests if all watchlist items are returned correctly.
     */
    @Test
    void getAllWatchlistItems_ShouldReturnItems() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        WatchlistItem item = new WatchlistItem(
                apple,
                "Watching Apple stock",
                BigDecimal.valueOf(180.00)
        );

        when(watchlistRepository.findAll()).thenReturn(List.of(item));

        List<WatchlistItem> result = watchlistService.getAllWatchlistItems();

        assertEquals(1, result.size());
        assertEquals("Watching Apple stock", result.get(0).getNote());
        assertEquals(BigDecimal.valueOf(180.00), result.get(0).getTargetPrice());

        verify(watchlistRepository, times(1)).findAll();
    }

    /**
     * Tests if a watchlist item is returned when it exists.
     */
    @Test
    void getWatchlistItemById_WhenItemExists_ShouldReturnItem() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        WatchlistItem item = new WatchlistItem(
                apple,
                "Watching Apple stock",
                BigDecimal.valueOf(180.00)
        );

        when(watchlistRepository.findById(1L)).thenReturn(Optional.of(item));

        WatchlistItem result = watchlistService.getWatchlistItemById(1L);

        assertEquals("Watching Apple stock", result.getNote());
        assertEquals("AAPL", result.getStock().getSymbol());

        verify(watchlistRepository, times(1)).findById(1L);
    }

    /**
     * Tests if exception is thrown when watchlist item does not exist.
     */
    @Test
    void getWatchlistItemById_WhenItemDoesNotExist_ShouldThrowException() {
        when(watchlistRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> watchlistService.getWatchlistItemById(999L)
        );

        assertEquals("Watchlist item was not found with id: 999", exception.getMessage());

        verify(watchlistRepository, times(1)).findById(999L);
    }

    /**
     * Tests if an existing stock can be added to watchlist.
     */
    @Test
    void addToWatchlist_ShouldSaveAndReturnWatchlistItem() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        WatchlistItemRequest request = new WatchlistItemRequest();
        request.setStockId(1L);
        request.setNote("Watching Apple stock");
        request.setTargetPrice(BigDecimal.valueOf(180.00));

        when(stockService.getStockById(1L)).thenReturn(apple);
        when(watchlistRepository.save(any(WatchlistItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WatchlistItem result = watchlistService.addToWatchlist(request);

        assertEquals("Watching Apple stock", result.getNote());
        assertEquals(BigDecimal.valueOf(180.00), result.getTargetPrice());
        assertEquals("AAPL", result.getStock().getSymbol());

        verify(stockService, times(1)).getStockById(1L);
        verify(watchlistRepository, times(1)).save(any(WatchlistItem.class));
    }

    /**
     * Tests if watchlist item deletion works correctly.
     */
    @Test
    void deleteWatchlistItem_WhenItemExists_ShouldDeleteItem() {
        Stock apple = new Stock(
                "AAPL",
                "Apple Inc.",
                "Technology",
                BigDecimal.valueOf(195.40),
                "USD"
        );

        WatchlistItem item = new WatchlistItem(
                apple,
                "Watching Apple stock",
                BigDecimal.valueOf(180.00)
        );

        when(watchlistRepository.findById(1L)).thenReturn(Optional.of(item));

        watchlistService.deleteWatchlistItem(1L);

        verify(watchlistRepository, times(1)).findById(1L);
        verify(watchlistRepository, times(1)).delete(item);
    }
}