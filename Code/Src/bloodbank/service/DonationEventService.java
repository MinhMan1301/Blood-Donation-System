package bloodbank.service;

import bloodbank.model.entity.DonationEvent;
import bloodbank.repo.DonationEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationEventService {
    @Autowired private DonationEventRepository eventRepository;

    public List<DonationEvent> findAll() { return eventRepository.findAll(); }
    public Optional<DonationEvent> findById(String id) { return eventRepository.findById(id); }
    public DonationEvent save(DonationEvent event) { return eventRepository.save(event); }
    public void deleteById(String id) { eventRepository.deleteById(id); }
}