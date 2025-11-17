package bloodbank.service;

import bloodbank.model.entity.BloodInventory;
import bloodbank.repo.BloodInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodInventoryService {
    @Autowired private BloodInventoryRepository inventoryRepository;

    public List<BloodInventory> findAll() { return inventoryRepository.findAll(); }
    public Optional<BloodInventory> findById(String id) { return inventoryRepository.findById(id); }
    public BloodInventory save(BloodInventory inventory) { return inventoryRepository.save(inventory); }
    public void deleteById(String id) { inventoryRepository.deleteById(id); }

    // Chức năng lọc nâng cao (cần phát triển thêm logic sau)
    // public List<BloodInventory> findFilteredInventory(...) {...}
}