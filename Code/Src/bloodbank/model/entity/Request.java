package bloodbank.model.entity;

import bloodbank.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Request")
public class Request {

    @Id
    @Column(name = "request_id", length = 20)
    private String requestId;

    // bank_id trong SQL là INT nhưng khóa chính BLOOD_BANK là VARCHAR(20) -> Giữ là String (VARCHAR)
    @Column(name = "bank_id", length = 20, nullable = false) 
    private String bankId; 

    // Dùng String do ENUM có ký tự đặc biệt 'A+', 'A-'...
    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @Column(name = "date_request", nullable = false)
    private LocalDate dateRequest;

    @Column(name = "date_response")
    private LocalDateTime dateResponse;

    // fulfilled_inventory_id trong SQL là INT nhưng Blood_Inventory là VARCHAR(20) -> Giữ là String
    @Column(name = "fulfilled_inventory_id", length = 20)
    private String fulfilledInventoryId;

    // --- Relationships ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donors_id")
    private Donor donor;
}