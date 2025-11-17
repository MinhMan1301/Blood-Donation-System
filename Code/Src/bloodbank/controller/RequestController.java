package bloodbank.controller;

import bloodbank.model.entity.Request;
import bloodbank.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    @Autowired private RequestService requestService;

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.findAll(); // GET /api/requests
    }

    @GetMapping("/{id}")
    public Request getRequestById(@PathVariable String id) {
        // GET /api/requests/{id}
        return requestService.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found: " + id));
    }

    @PostMapping
    public Request createRequest(@RequestBody Request request) {
        return requestService.save(request); // POST /api/requests
    }

    @PutMapping("/{id}")
    public Request updateRequest(@PathVariable String id, @RequestBody Request requestDetails) {
        // PUT /api/requests/{id}
        return requestService.findById(id).map(request -> {
            // Logic cập nhật chi tiết...
            requestDetails.setRequest_id(id); // Đảm bảo ID không thay đổi
            return requestService.save(requestDetails);
        }).orElseThrow(() -> new RuntimeException("Request not found: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable String id) {
        // DELETE /api/requests/{id}
        requestService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}