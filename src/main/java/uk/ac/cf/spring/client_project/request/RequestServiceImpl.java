package uk.ac.cf.spring.client_project.request;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    public RequestServiceImpl(RequestRepository aRequestRepository) {
        this.requestRepository = aRequestRepository;
    }

    public List<Request> getAllRequests() {return requestRepository.getAllRequests(); }
    public List<Request> getPendingRequests() {
        return requestRepository.getPendingRequests();
    }
    public Request save(Request request) {
        requestRepository.save(request);
        return request;
    }
    public boolean validateUserId(Long userId) {
        return requestRepository.userExists(userId);
    }

    @Override
    public void updateRequestStatus(Long requestId, RequestStatus requestStatus) {
        // Retrieve the request by ID
        Request request = requestRepository.getRequest(requestId);

        // If the request exists
        if (request != null) {

            if (request.getStatus() == RequestStatus.PENDING) {

                request.setStatus(requestStatus);
                requestRepository.save(request);  // Save the updated request
                System.out.println("Request status updated to: " + requestStatus);
            } else {
                System.out.println("Request is not in 'PENDING' status, cannot update.");
            }

        }
    }
}

