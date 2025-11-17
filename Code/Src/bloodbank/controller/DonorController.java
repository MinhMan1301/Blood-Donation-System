package bloodbank.controller;

import bloodbank.model.entity.Donor;
import bloodbank.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {
    @Autowired private DonorService donorService;

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.findAll(); // GET /api/donors
    }

    @GetMapping("/{id}")
    public Donor getDonorById(@PathVariable String id) {
        // GET /api/donors/{id}
        return donorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found: " + id));
    }

    @PutMapping("/{id}")
    public Donor updateDonor(@PathVariable String id, @RequestBody Donor donorDetails) {
        // PUT /api/donors/{id}
        return donorService.findById(id).map(donor -> {
            // Logic cập nhật chi tiết...
            donorDetails.setDonors_id(id); // Đảm bảo ID không thay đổi
            return donorService.save(donorDetails);
        }).orElseThrow(() -> new RuntimeException("Donor not found: " + id));
    }
}