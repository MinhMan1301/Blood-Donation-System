package bloodbank.service;

import bloodbank.model.entity.Donor;
import bloodbank.repo.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {
    @Autowired private DonorRepository donorRepository;

    public List<Donor> findAll() { return donorRepository.findAll(); }
    public Optional<Donor> findById(String id) { return donorRepository.findById(id); }
    public Donor save(Donor donor) { return donorRepository.save(donor); }
    public void deleteById(String id) { donorRepository.deleteById(id); }
}