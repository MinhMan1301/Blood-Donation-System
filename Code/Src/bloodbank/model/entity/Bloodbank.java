package bloodbank.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BLOOD_BANK")
public class BloodBank {

    @Id
    @Column(name = "bank_id", length = 20)
    private String bankId;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "contact_phone", length = 15)
    private String contactPhone;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "volume", precision = 10, scale = 2)
    private BigDecimal volume;

    // --- Relationships ---

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BloodInventory> inventories;

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DonationEvent> donationEvents;
    
    // Phía nghịch của Many-to-Many với Doctor
    @ManyToMany(mappedBy = "bloodBanks", fetch = FetchType.LAZY)
    private Set<Doctor> doctors;

    // Liên kết tới bác sĩ được gán
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_doctor", referencedColumnName = "doctor_id")
    private Doctor assignedDoctor;
    
    // One-to-One với Request (theo schema của bạn)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", referencedColumnName = "request_id")
    private Request request;
}