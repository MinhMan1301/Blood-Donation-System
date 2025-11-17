package bloodbank.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Donation_Event")
public class DonationEvent {

    @Id
    @Column(name = "donation_id", length = 20)
    private String donationId;

    @Column(name = "Date_event", nullable = false)
    private LocalDate dateEvent;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "volume_collected", precision = 10, scale = 2, nullable = false)
    private BigDecimal volumeCollected;

    // --- Relationships ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    private BloodBank bloodBank;

    // Phía nghịch của Many-to-Many với Donor
    @ManyToMany(mappedBy = "donationEvents", fetch = FetchType.LAZY)
    private Set<Donor> donors;
}