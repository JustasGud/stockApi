package com.example.stockportfolioapi.repository;

import com.example.stockportfolioapi.model.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository responsible for database operations related to watchlist items.
 */
public interface WatchlistRepository extends JpaRepository<WatchlistItem, Long> {
}