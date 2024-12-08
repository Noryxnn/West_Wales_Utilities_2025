package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.request.RequestRepository;

@Service
public class AdminServiceImpl implements AdminService {
    private RequestRepository requestRepository;
    public AdminServiceImpl(RequestRepository requestRepository) {

        this.requestRepository = requestRepository;
    }


}
