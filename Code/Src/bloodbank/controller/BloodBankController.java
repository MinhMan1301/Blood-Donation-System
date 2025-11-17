package bloodbank.controller;

import bloodbank.model.entity.BloodBank;
import bloodbank.service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blood-banks")
public class BloodBankController {
    @Autowired private BloodBankService bankService;

    @GetMapping
    public List<BloodBank> getAllBloodBanks() {
        return bankService.findAll(); // GET /api/blood-banks
    }

    @GetMapping("/{id}")
    public BloodBank getBloodBankById(@PathVariable String id) {
        // GET /api/blood-banks/{id}
        return bankService.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood bank not found: " + id));
    }

    @PutMapping("/{id}")
    public BloodBank updateBloodBank(@PathVariable String id, @RequestBody BloodBank bankDetails) {
        // PUT /api/blood-banks/{id}
        return bankService.findById(id).map(bank -> {
            // Logic cập nhật chi tiết...
            bankDetails.setBank_id(id); // Đảm bảo ID không thay đổi
            return bankService.save(bankDetails);
        }).orElseThrow(() -> new RuntimeException("Blood bank not found: " + id));
    }
}