package com.hector.portfolio_base.controller;

import com.hector.portfolio_base.InventoryItem;
import com.hector.portfolio_base.repository.InventoryItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class InventoryItemController {

    private final InventoryItemRepository repository;

    // Direct constructor injection matching your project structure
    public InventoryItemController(InventoryItemRepository repository) {
        this.repository = repository;
    }

    // Endpoint: GET http://localhost:8080/api/parts
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllParts() {
        List<InventoryItem> parts = repository.findAll();
        return ResponseEntity.ok(parts);
    }

    // Endpoint: GET http://localhost:8080/api/parts/critical
    @GetMapping("/critical")
    public ResponseEntity<List<InventoryItem>> getCriticalStock() {
        List<InventoryItem> criticalParts = repository.findCriticalStockItems();
        return ResponseEntity.ok(criticalParts);
    }

    /**
     * Endpoint to register and persist a new component into the system database.
     * POST /api/parts
     *
     * @param newItem Data payload containing details of the new inventory item.
     * @return The persisted entity along with a 201 Created HTTP status code.
     */
    @org.springframework.web.bind.annotation.PostMapping
    public ResponseEntity<InventoryItem> createPart(@jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody InventoryItem newItem) {
        InventoryItem savedItem = repository.save(newItem);
        return new ResponseEntity<>(savedItem, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updatePart(@PathVariable Long id, @RequestBody InventoryItem updatedItem) {
        return repository.findById(id)
                .map(existingItem -> {
                    // Update the core fields of the automotive part
                    existingItem.setName(updatedItem.getName());
                    existingItem.setSku(updatedItem.getSku());
                    existingItem.setStockQuantity(updatedItem.getStockQuantity());
                    existingItem.setMinimumRequired(updatedItem.getMinimumRequired());
                    existingItem.setManufacturer(updatedItem.getManufacturer());

                    InventoryItem savedProduct = repository.save(existingItem);
                    return ResponseEntity.ok(savedProduct); // Returns 200 OK with the updated data
                })
                .orElse(ResponseEntity.notFound().build()); // Returns 404 Not Found if the ID does not exist
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePart(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // Returns 204 No Content for a successful deletion
        }
        return ResponseEntity.notFound().build(); // Returns 404 Not Found if the part doesn't exist
    }
}