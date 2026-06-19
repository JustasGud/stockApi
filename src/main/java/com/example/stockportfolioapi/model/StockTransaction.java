package com.example.stockportfolioapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a stock buy or sell transaction.
 */
@Entity
@Table(name = "stock_transactions")
public class StockTransaction {

    /**
     * Primary key of the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Stock related to this transaction.
     */
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    /**
     * Type of transaction: BUY or SELL.
     */
    @NotNull(message = "Transaction type is required.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    /**
     * Number of shares bought or sold.
     */
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Column(nullable = false)
    private int quantity;

    /**
     * Price per share at the time of transaction.
     */
    @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Date when the transaction happened.
     */
    @NotNull(message = "Transaction date is required.")
    @Column(nullable = false)
    private LocalDate transactionDate;

    public StockTransaction() {
    }

    public StockTransaction(
            Stock stock,
            TransactionType transactionType,
            int quantity,
            BigDecimal price,
            LocalDate transactionDate
    ) {
        this.stock = stock;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
        this.transactionDate = transactionDate;
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