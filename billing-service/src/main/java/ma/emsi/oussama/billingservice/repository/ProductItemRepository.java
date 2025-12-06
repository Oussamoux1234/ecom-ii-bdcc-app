package ma.emsi.oussama.billingservice.repository;

import ma.emsi.oussama.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
