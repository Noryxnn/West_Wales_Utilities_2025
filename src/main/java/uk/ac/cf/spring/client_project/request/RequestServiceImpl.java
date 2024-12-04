package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
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


    @Transactional
    public boolean confirmRequest(Long requestId) {
        // Fetch request from the database using repository
        Request request = requestRepository.getRequest(requestId);

        if (request != null) {

            request.setApproved(true);

            // Save the updated request back to the repository
            requestRepository.save(request);
        }
        return false;
    }







}
