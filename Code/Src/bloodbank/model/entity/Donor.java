package bloodbank.model.entity;

import bloodbank.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Donors")
public class Donor {

    @Id
    @Column(name = "donors_id", length = 20)
    private String donorsId;

    @Column(name = "ssn", length = 15, unique = true, nullable = false)
    private String ssn;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "DateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "Gender", length = 10, nullable = false)
    private Gender gender;

    @Column(name = "last_donation_date")
    private LocalDate lastDonationDate;

    // --- Relationships ---

    @OneToMany(mappedBy = "donor", fetch = FetchType.LAZY)
    private Set<Request> requests;
    
    // Many-to-Many với DonationEvent (qua bảng Donors_DonationEvent)
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Donors_DonationEvent",
        joinColumns = @JoinColumn(name = "donors_id"),
        inverseJoinColumns = @JoinColumn(name = "donation_id")
    )
    private Set<DonationEvent> donationEvents;
}