package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    public RequestServiceImpl(RequestRepository aRequestRepository) {
        this.requestRepository = aRequestRepository;
    }

    public List<Request> getAllRequests() {return requestRepository.getAllRequests(); }
    public List<Request> getOpenRequests() {
        return requestRepository.getOpenRequests();
    }
    public Request getRequest(Long requestId) {
        return requestRepository.getRequest(requestId);
    }
    public Request save(Request request) {
        requestRepository.save(request);
        return request;
    }
    public boolean validateUserId(Long userId) {
        return requestRepository.userExists(userId);
    };

    public void acceptRequest(Long requestId) {requestRepository.acceptRequest(requestId);
    }
    public void denyRequest(Long requestId) {requestRepository.denyRequest(requestId); }

}

