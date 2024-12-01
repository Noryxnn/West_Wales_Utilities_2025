package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;


@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository aRequestRepository) {
        this.requestRepository = aRequestRepository;
    }

    public void save(Request request) {
        requestRepository.save(request);
    }
    public boolean validateUserId(Long userId) {
        return requestRepository.userExists(userId);
    }

}
