package ma.emsi.oussama.billingservice.repository;

import ma.emsi.oussama.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository  extends JpaRepository<Bill, Long> {
}
