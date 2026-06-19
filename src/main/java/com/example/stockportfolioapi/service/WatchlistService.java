package com.example.stockportfolioapi.service;

import com.example.stockportfolioapi.dto.WatchlistItemRequest;
import com.example.stockportfolioapi.exception.ResourceNotFoundException;
import com.example.stockportfolioapi.model.Stock;
import com.example.stockportfolioapi.model.WatchlistItem;
import com.example.stockportfolioapi.repository.WatchlistRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that contains business logic for watchlist operations.
 */
@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final StockService stockService;

    /**
     * Constructor injection of required dependencies.
     *
     * @param watchlistRepository repository used for watchlist database operations
     * @param stockService service used to find stocks before adding them to watchlist
     */
    public WatchlistService(WatchlistRepository watchlistRepository, StockService stockService) {
        this.watchlistRepository = watchlistRepository;
        this.stockService = stockService;
    }

    /**
     * Returns all watchlist items.
     *
     * @return all watchlist items
     */
    @Cacheable("watchlist")
    public List<WatchlistItem> getAllWatchlistItems() {
        return watchlistRepository.findAll();
    }

    /**
     * Returns one watchlist item by ID.
     *
     * @param id watchlist item ID
     * @return found watchlist item
     */
    @Cacheable(value = "watchlistItem", key = "#id")
    public WatchlistItem getWatchlistItemById(Long id) {
        return watchlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist item was not found with id: " + id));
    }

    /**
     * Adds an existing stock to the watchlist.
     *
     * @param request request containing stock ID, note and target price
     * @return created watchlist item
     */
    @CacheEvict(value = "watchlist", allEntries = true)
    public WatchlistItem addToWatchlist(WatchlistItemRequest request) {
        Stock stock = stockService.getStockById(request.getStockId());

        WatchlistItem item = new WatchlistItem();
        item.setStock(stock);
        item.setNote(request.getNote());
        item.setTargetPrice(request.getTargetPrice());

        return watchlistRepository.save(item);
    }

    /**
     * Deletes a watchlist item by ID.
     *
     * @param id watchlist item ID
     */
    @CacheEvict(value = {"watchlist", "watchlistItem"}, allEntries = true)
    public void deleteWatchlistItem(Long id) {
        WatchlistItem item = getWatchlistItemById(id);
        watchlistRepository.delete(item);
    }
}