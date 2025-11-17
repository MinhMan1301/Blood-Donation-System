package bloodbank.service;

import bloodbank.model.entity.Doctor;
import bloodbank.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired private DoctorRepository doctorRepository;

    public List<Doctor> findAll() { return doctorRepository.findAll(); }
    public Optional<Doctor> findById(String id) { return doctorRepository.findById(id); }
    public Doctor save(Doctor doctor) { return doctorRepository.save(doctor); }
    public void deleteById(String id) { doctorRepository.deleteById(id); }
}