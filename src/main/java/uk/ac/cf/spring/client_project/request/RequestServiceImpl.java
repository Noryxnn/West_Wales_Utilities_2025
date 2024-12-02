package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;


@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository aRequestRepository) {
        this.requestRepository = aRequestRepository;
    }

    public Request save(Request request) {
        return requestRepository.save(request);
    }
    public boolean validateUserId(Long userId) {
        return requestRepository.userExists(userId);
    }
    public Request findById(Long requestId) {
        return requestRepository.findById(requestId);
    }
}
