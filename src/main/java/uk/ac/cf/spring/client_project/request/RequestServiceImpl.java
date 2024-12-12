package uk.ac.cf.spring.client_project.request;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    public RequestServiceImpl(RequestRepository aRequestRepository, UserRepository userRepository) {
        this.requestRepository = aRequestRepository;
        this.userRepository = userRepository;
    }

    public List<Request> getAllRequests() {return requestRepository.getAllRequests(); }
    public List<Request> getPendingRequests() {
        return requestRepository.getPendingRequests();
    }
    public Request save(Request request) {
        if (request.getUserId() == null || request.getUserId() == 0) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            Optional<uk.ac.cf.spring.client_project.user.User> user1 = userRepository.findByEmail(username);
            user1.ifPresent(value -> request.setUserId(Long.valueOf(value.getUserId())));
        }
        if (request.getRequestDate() == null) {
            request.setRequestDate(LocalDateTime.now());
        }
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

