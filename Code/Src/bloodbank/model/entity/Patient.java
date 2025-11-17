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
@Table(name = "Patients")
public class Patient {

    @Id
    @Column(name = "patient_id", length = 20)
    private String patientId;

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

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "donation_date")
    private LocalDate donationDate;

    // --- Relationships ---

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Request> requests;
}