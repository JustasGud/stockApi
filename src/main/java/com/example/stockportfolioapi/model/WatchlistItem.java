package com.example.stockportfolioapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * Represents one stock saved in the user's watchlist.
 * A watchlist item is connected to one stock.
 */
@Entity
@Table(name = "watchlist_items")
public class WatchlistItem {

    /**
     * Primary key of the watchlist item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Stock that is saved in the watchlist.
     * Many watchlist items can reference stocks, but one item belongs to one stock.
     */
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    /**
     * User note about why this stock is watched.
     */
    @NotBlank(message = "Note is required.")
    @Column(nullable = false)
    private String note;

    /**
     * Price at which the user is interested in the stock.
     */
    @DecimalMin(value = "0.01", message = "Target price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal targetPrice;

    public WatchlistItem() {
    }

    public WatchlistItem(Stock stock, String note, BigDecimal targetPrice) {
        this.stock = stock;
        this.note = note;
        this.targetPrice = targetPrice;
    }

    public Long getId() {
        return id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }
}