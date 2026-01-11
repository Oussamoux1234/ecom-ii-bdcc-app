package ma.emsi.inventaryservice.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Consumer;

/**
 * Kafka event handler for processing billing events.
 * Consumes events from BILLING_EVENTS topic and updates inventory accordingly.
 */
@Component
public class StockUpdateHandler {

    /**
     * Record matching the BillEvent structure from billing-service
     */
    public record BillEvent(
            Long billId,
            Long customerId,
            Double totalAmount,
            Date billingDate,
            int itemCount) {
    }

    /**
     * Consumes billing events and processes stock updates.
     * This bean is wired via spring.cloud.function.definition in properties.
     */
    @Bean
    public Consumer<BillEvent> billingEventProcessor() {
        return billEvent -> {
            System.out.println("===========================================");
            System.out.println("📦 INVENTORY: Received Bill Event");
            System.out.println("===========================================");
            System.out.println("Bill ID: " + billEvent.billId());
            System.out.println("Customer ID: " + billEvent.customerId());
            System.out.println("Total Amount: $" + billEvent.totalAmount());
            System.out.println("Billing Date: " + billEvent.billingDate());
            System.out.println("Item Count: " + billEvent.itemCount());
            System.out.println("-------------------------------------------");
            System.out.println("📉 Processing stock update for " + billEvent.itemCount() + " items...");

            // TODO: Implement actual stock deduction logic
            // Example: productRepository.decrementStock(productId, quantity);

            System.out.println("✅ Stock update completed for Bill #" + billEvent.billId());
            System.out.println("===========================================");
        };
    }
}
