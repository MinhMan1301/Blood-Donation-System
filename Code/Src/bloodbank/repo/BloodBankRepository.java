package bloodbank.repo;

import bloodbank.model.entity.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, String> {
}