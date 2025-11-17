package bloodbank.service;

import bloodbank.model.entity.BloodBank;
import bloodbank.repo.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodBankService {
    @Autowired private BloodBankRepository bankRepository;

    public List<BloodBank> findAll() { return bankRepository.findAll(); }
    public Optional<BloodBank> findById(String id) { return bankRepository.findById(id); }
    public BloodBank save(BloodBank bank) { return bankRepository.save(bank); }
    public void deleteById(String id) { bankRepository.deleteById(id); }
}