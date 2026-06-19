package com.example.stockportfolioapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Request object used when adding a stock to the watchlist.
 * The client sends stockId instead of the full Stock object.
 */
public class WatchlistItemRequest {

    /**
     * ID of the stock that should be added to the watchlist.
     */
    @NotNull(message = "Stock ID is required.")
    private Long stockId;

    /**
     * User note about the watched stock.
     */
    @NotBlank(message = "Note is required.")
    private String note;

    /**
     * Target price for the watched stock.
     */
    @NotNull(message = "Target price is required.")
    @DecimalMin(value = "0.01", message = "Target price must be greater than 0.")
    private BigDecimal targetPrice;

    public WatchlistItemRequest() {
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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