package com.example.stockportfolioapi.dto;

import com.example.stockportfolioapi.model.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request object used when creating a stock transaction.
 */
public class TransactionRequest {

    /**
     * Type of transaction: BUY or SELL.
     */
    @NotNull(message = "Transaction type is required.")
    private TransactionType transactionType;

    /**
     * Number of shares bought or sold.
     */
    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    /**
     * Price per share.
     */
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    private BigDecimal price;

    /**
     * Date of the transaction.
     */
    @NotNull(message = "Transaction date is required.")
    private LocalDate transactionDate;

    public TransactionRequest() {
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}