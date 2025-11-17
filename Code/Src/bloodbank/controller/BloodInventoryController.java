package bloodbank.controller;

import bloodbank.model.entity.BloodInventory;
import bloodbank.service.BloodInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blood-inventory")
public class BloodInventoryController {
    @Autowired private BloodInventoryService inventoryService;

    public List<BloodInventory> getAllInventory() {
        return inventoryService.findAll(); // GET /api/blood-inventory
    }

    @GetMapping("/{id}")
    @GetMapping
    public BloodInventory getInventoryById(@PathVariable String id) {
        // GET /api/blood-inventory/{id}
        return inventoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood unit not found: " + id));
    }

    @PutMapping("/{id}")
    public BloodInventory updateInventory(@PathVariable String id, @RequestBody BloodInventory inventoryDetails) {
        // PUT /api/blood-inventory/{id}
        // Kiểm tra xem unit_id có tồn tại không trước khi lưu
        return inventoryService.findById(id).map(inventory -> {
            // Logic cập nhật chi tiết... (cần thêm các trường để cập nhật)
            inventoryDetails.setUnit_id(id); // Đảm bảo ID không thay đổi
            return inventoryService.save(inventoryDetails);
        }).orElseThrow(() -> new RuntimeException("Blood unit not found: " + id));
    }
}