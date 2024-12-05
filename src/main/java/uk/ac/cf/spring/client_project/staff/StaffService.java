package uk.ac.cf.spring.client_project.staff;

import java.util.HashMap;

public interface StaffService {
     boolean isVisitorApproved(Long userId);
     HashMap<String, Object> stringToHashMap(String input);
}
