package bloodbank.controller;

import bloodbank.model.entity.Patient;
import bloodbank.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.findAll(); // GET /api/patients
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable String id) {
        // GET /api/patients/{id}
        return patientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found: " + id));
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable String id, @RequestBody Patient patientDetails) {
        // PUT /api/patients/{id}
        return patientService.findById(id).map(patient -> {
            // Logic cập nhật chi tiết...
            patientDetails.setPatient_id(id); // Đảm bảo ID không thay đổi
            return patientService.save(patientDetails);
        }).orElseThrow(() -> new RuntimeException("Patient not found: " + id));
    }
}