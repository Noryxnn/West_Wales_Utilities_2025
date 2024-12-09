package uk.ac.cf.spring.client_project.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestRepository;
import uk.ac.cf.spring.client_project.request.RequestStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

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
            if (request.getStatus() == RequestStatus.APPROVED
                    && (request.getVisitStartDate().isBefore(currentDate) || request.getVisitStartDate().isEqual(currentDate))
                    && request.getVisitEndDate().isAfter(currentDate)) {
                logger.info("Approved request found for user {}", userId);
                return true;
            }
        }
        logger.info("No approved request found for user {}", userId);
        return false;
    }

    public HashMap<String, Object> stringToHashMap(String input) {
        input = input.substring(1, input.length() - 1);

        HashMap<String, Object> map = new HashMap<>();
        String[] splitInput = input.split(", ");

        for (String s : splitInput) {
            String[] splitItem = s.split("=", 2);
            String key = splitItem[0].trim();
            String value = splitItem[1].trim();

            map.put(key, value);
        }
        return map;
    }

}
