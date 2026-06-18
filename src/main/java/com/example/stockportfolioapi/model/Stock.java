package com.example.stockportfolioapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Represents a stock in the system.
 * This class becomes a database table because it uses the @Entity annotation.
 */
@Entity
@Table(name = "stocks")
public class Stock {

    /**
     * Primary key of the stock table.
     * The value is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Stock market symbol, for example AAPL, TSLA, MSFT.
     */
    @NotBlank(message = "Stock symbol is required.")
    @Size(max = 10, message = "Stock symbol cannot be longer than 10 characters.")
    @Column(nullable = false, unique = true, length = 10)
    private String symbol;

    /**
     * Company name, for example Apple Inc.
     */
    @NotBlank(message = "Company name is required.")
    @Column(nullable = false)
    private String companyName;

    /**
     * Business sector, for example Technology or Finance.
     */
    @NotBlank(message = "Sector is required.")
    @Column(nullable = false)
    private String sector;

    /**
     * Current stock price.
     */
    @DecimalMin(value = "0.01", message = "Current price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal currentPrice;

    /**
     * Currency of the stock price, for example USD or EUR.
     */
    @NotBlank(message = "Currency is required.")
    @Size(min = 3, max = 3, message = "Currency must contain exactly 3 characters.")
    @Column(nullable = false, length = 3)
    private String currency;

    public Stock() {
    }

    public Stock(String symbol, String companyName, String sector, BigDecimal currentPrice, String currency) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}