package bloodbank.controller;

import bloodbank.model.entity.Doctor;
import bloodbank.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.findAll(); // GET /api/doctors
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable String id) {
        // GET /api/doctors/{id}
        return doctorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found: " + id));
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable String id, @RequestBody Doctor doctorDetails) {
        // PUT /api/doctors/{id}
        return doctorService.findById(id).map(doctor -> {
            // Logic cập nhật chi tiết...
            doctorDetails.setDoctorId(id); // Đảm bảo ID không thay đổi
            return doctorService.save(doctorDetails);
        }).orElseThrow(() -> new RuntimeException("Doctor not found: " + id));
    }
}