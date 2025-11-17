package bloodbank.repo;

import bloodbank.model.entity.DonationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationEventRepository extends JpaRepository<DonationEvent, String> {
}