package uk.ac.cf.spring.client_project.staff;

import java.util.HashMap;

public interface StaffService {
     /**
      * Checks if the visitor is approved for visit
      *
      * @param userId The id of the visitor
      * @return approval status
      */
     boolean isVisitorApproved(Long userId);

     /**
      * Converts QR code data to a HashMap
      *
      * @param input The QR code data
      * @return Data as a HashMap
      */
     HashMap<String, Object> stringToHashMap(String input);

}
