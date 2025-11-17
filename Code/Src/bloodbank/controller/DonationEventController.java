package bloodbank.controller;

import bloodbank.model.entity.DonationEvent;
import bloodbank.service.DonationEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donation-events")
public class DonationEventController {
    @Autowired private DonationEventService eventService;

    @GetMapping
    public List<DonationEvent> getAllEvents() {
        return eventService.findAll(); // GET /api/donation-events
    }

    @GetMapping("/{id}")
    public DonationEvent getEventById(@PathVariable String id) {
        // GET /api/donation-events/{id}
        return eventService.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation event not found: " + id));
    }

    @PutMapping("/{id}")
    public DonationEvent updateEvent(@PathVariable String id, @RequestBody DonationEvent eventDetails) {
        // PUT /api/donation-events/{id}
        return eventService.findById(id).map(event -> {
            // Logic cập nhật chi tiết...
            eventDetails.setDonation_id(id); // Đảm bảo ID không thay đổi
            return eventService.save(eventDetails);
        }).orElseThrow(() -> new RuntimeException("Donation event not found: " + id));
    }
}