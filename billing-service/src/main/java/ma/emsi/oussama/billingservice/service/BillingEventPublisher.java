package ma.emsi.oussama.billingservice.service;

import ma.emsi.oussama.billingservice.entities.Bill;
import ma.emsi.oussama.billingservice.entities.ProductItem;
import ma.emsi.oussama.billingservice.events.BillEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

/**
 * Service for publishing billing events to Kafka.
 * Uses Spring Cloud Stream's StreamBridge for dynamic destination binding.
 */
@Service
public class BillingEventPublisher {

    private final StreamBridge streamBridge;

    public BillingEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * Calculates total amount from product items.
     */
    private double calculateTotalAmount(Bill bill) {
        if (bill.getProductItems() == null || bill.getProductItems().isEmpty()) {
            return 0.0;
        }
        return bill.getProductItems().stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Publishes a bill created event to the BILLING_EVENTS topic.
     * 
     * @param bill The bill entity that was created
     */
    public void publishBillCreated(Bill bill) {
        double totalAmount = calculateTotalAmount(bill);
        int itemCount = bill.getProductItems() != null ? bill.getProductItems().size() : 0;

        BillEvent event = new BillEvent(
                bill.getId(),
                bill.getCustomerId(),
                totalAmount,
                bill.getBillingDate(),
                itemCount);

        boolean sent = streamBridge.send("billingEventProducer-out-0", event);
        if (sent) {
            System.out.println("📨 Published BillEvent: billId=" + event.billId() +
                    ", customerId=" + event.customerId() +
                    ", amount=" + event.totalAmount());
        }
    }

    /**
     * Publishes an invoice paid event.
     * 
     * @param billId The ID of the paid bill
     * @param amount The amount paid
     */
    public void publishInvoicePaid(Long billId, Double amount) {
        BillEvent event = new BillEvent(billId, null, amount, new java.util.Date(), 0);
        streamBridge.send("billingEventProducer-out-0", event);
        System.out.println("💰 Published InvoicePaid event: billId=" + billId);
    }
}
