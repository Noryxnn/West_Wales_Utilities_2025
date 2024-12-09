package uk.ac.cf.spring.client_project.staff;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestRepository;

import java.util.HashMap;

@Service
public class StaffServiceImpl implements StaffService {
    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    RequestRepository requestRepository;

    @Autowired
    public StaffServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public boolean isVisitorApproved(Long userId) {
        List<Request> requests = requestRepository.findByUserId(userId);
        LocalDate currentDate = LocalDate.now();
        for (Request request : requests) {
            if (request.isApproved()
                    && (request.getVisitStartDate().isBefore(currentDate) || request.getVisitStartDate().isEqual(currentDate))
                    && request.getVisitEndDate().isAfter(currentDate)) {
                logger.info("Approved request found for user {}", userId);
                return true;
            }
        }
        logger.info("No approved request found for user {}", userId);
        return false;
    }
}
