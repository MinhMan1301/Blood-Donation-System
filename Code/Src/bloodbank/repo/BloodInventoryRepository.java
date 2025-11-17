package bloodbank.repo;

import bloodbank.model.entity.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, String> {
}