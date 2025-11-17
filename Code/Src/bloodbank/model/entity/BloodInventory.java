package bloodbank.model.entity;

import bloodbank.model.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Blood_Inventory")
public class BloodInventory {

    @Id
    @Column(name = "unit_id", length = 20)
    private String unitId;

    // Dùng String do ENUM có ký tự đặc biệt 'A+', 'A-'...
    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    // Dùng String do ENUM có ký tự đặc biệt '+', '-'
    @Column(name = "RH", nullable = false)
    private String rh;

    @Column(name = "volume_Litter", precision = 10, scale = 2, nullable = false)
    private BigDecimal volumeLitter;

    @Column(name = "donated_date", nullable = false)
    private LocalDate donatedDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InventoryStatus status;

    // --- Relationships ---
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    private BloodBank bloodBank;
}