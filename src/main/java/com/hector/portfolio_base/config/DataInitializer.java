package com.hector.portfolio_base.config;




import com.hector.portfolio_base.InventoryItem;
import com.hector.portfolio_base.repository.InventoryItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(InventoryItemRepository repository) {
        return args -> {
            // Check if the database already has data to prevent duplicates on restart
            if (repository.count() == 0) {
                System.out.println("🚀 [INFO] Database is empty. Seeding German automotive inventory data...");

                InventoryItem sparkPlug = new InventoryItem(
                        "DE-BOSCH-7732X",
                        "Double Platinum Spark Plug (Zündkerze)",
                        "Bosch",
                        150,  // stock quantity
                        50,   // minimum required threshold
                        new BigDecimal("14.99")
                );

                InventoryItem tire = new InventoryItem(
                        "DE-CONT-9911A",
                        "AllSeasonContact 2 Tire (Allwetterreifen)",
                        "Continental",
                        80,   // stock quantity
                        20,   // minimum required threshold
                        new BigDecimal("119.50")
                );

                InventoryItem brakePad = new InventoryItem(
                        "DE-ATE-4422B",
                        "Ceramic Brake Pad Set (Bremsbelagsatz)",
                        "ATE",
                        12,   // stock quantity - CRITICAL: This is below the minimum threshold!
                        30,   // minimum required threshold
                        new BigDecimal("85.00")
                );

                // Save all components to PostgreSQL at once
                repository.saveAll(List.of(sparkPlug, tire, brakePad));

                System.out.println("✅ [INFO] Database seeding completed successfully!");
            } else {
                System.out.println("ℹ️ [INFO] Database already contains records. Skipping seeding.");
            }
        };
    }
}