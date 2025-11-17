package bloodbank.service;

import bloodbank.model.entity.Request;
import bloodbank.repo.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    @Autowired private RequestRepository requestRepository;

    public List<Request> findAll() { return requestRepository.findAll(); }
    public Optional<Request> findById(String id) { return requestRepository.findById(id); }
    public Request save(Request request) { return requestRepository.save(request); }
    public void deleteById(String id) { requestRepository.deleteById(id); }
}