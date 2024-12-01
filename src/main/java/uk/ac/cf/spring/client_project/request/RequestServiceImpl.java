package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    public RequestServiceImpl(RequestRepository aRequestRepository) {
        this.requestRepository = aRequestRepository;
    }
    public List<Request> getOpenRequests() {
        return requestRepository.getOpenRequests();
    }
    public Request getRequest(Long requestId) {
        return requestRepository.getRequest(requestId);
    }
    public void save(Request request) {
        requestRepository.save(request);
    }
    public boolean validateUserId(Long userId) {
        return requestRepository.userExists(userId);
    }

}
