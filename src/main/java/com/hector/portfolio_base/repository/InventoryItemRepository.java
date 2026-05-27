package com.hector.portfolio_base.repository;

import com.hector.portfolio_base.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    /**
     * Custom query to filter items where current inventory levels fall below the established safety stock threshold.
     * Used for automated reordering alerts and dashboard metrics.
     */
    @Query("SELECT i FROM InventoryItem i WHERE i.stockQuantity < i.minimumRequired")
    List<InventoryItem> findCriticalStockItems();
}