package com.hector.portfolio_base;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Domain model representing a high-precision industrial or automotive component
 * optimized for European logistics tracking and enterprise inventory management.
 */
@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private Integer minimumRequired;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceEuro;

    public InventoryItem() {}

    public InventoryItem(String sku, String name, String manufacturer, Integer stockQuantity, Integer minimumRequired, BigDecimal priceEuro) {
        this.sku = sku;
        this.name = name;
        this.manufacturer = manufacturer;
        this.stockQuantity = stockQuantity;
        this.minimumRequired = minimumRequired;
        this.priceEuro = priceEuro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getMinimumRequired() {
        return minimumRequired;
    }

    public void setMinimumRequired(Integer minimumRequired) {
        this.minimumRequired = minimumRequired;
    }

    public BigDecimal getPriceEuro() {
        return priceEuro;
    }

    public void setPriceEuro(BigDecimal priceEuro) {
        this.priceEuro = priceEuro;
    }
}