package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestRepository;

import java.util.List;

    @Service
    public class AdminServiceImpl implements AdminService {
        private RequestRepository requestRepository;
        public AdminServiceImpl(RequestRepository requestRepository) {

            this.requestRepository = requestRepository;
        }


        @Override
        public List<Request> findPendingRequests() {
            // Implement logic to find pending requests
            return requestRepository.getOpenRequests();  // Adjust according to your logic
        }

}
