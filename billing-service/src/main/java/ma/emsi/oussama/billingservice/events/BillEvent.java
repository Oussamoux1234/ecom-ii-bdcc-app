package ma.emsi.oussama.billingservice.events;

import java.util.Date;

/**
 * Event record representing a billing event for Kafka messaging.
 * Published when a bill is created or updated.
 */
public record BillEvent(
        Long billId,
        Long customerId,
        Double totalAmount,
        Date billingDate,
        int itemCount) {
}
