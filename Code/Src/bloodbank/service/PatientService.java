package bloodbank.service;

import bloodbank.model.entity.Patient;
import bloodbank.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired private PatientRepository patientRepository;

    public List<Patient> findAll() { return patientRepository.findAll(); }
    public Optional<Patient> findById(String id) { return patientRepository.findById(id); }
    public Patient save(Patient patient) { return patientRepository.save(patient); }
    public void deleteById(String id) { patientRepository.deleteById(id); }
}