package bloodbank.model.entity;

import bloodbank.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Doctor")
public class Doctor {

    @Id
    @Column(name = "doctor_id", length = 20)
    private String doctorId;

    @Column(name = "ssn", length = 15, unique = true, nullable = false)
    private String ssn;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "specialization", length = 100, nullable = false)
    private String specialization;

    // --- Relationships ---

    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Request> requests;

    // Many-to-Many với BloodBank (qua bảng Doctor_BloodBank)
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Doctor_BloodBank",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "bank_id")
    )
    private Set<BloodBank> bloodBanks;
}