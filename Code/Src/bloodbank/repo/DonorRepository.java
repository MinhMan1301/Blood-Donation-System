package bloodbank.repo;

import bloodbank.model.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, String> {
}