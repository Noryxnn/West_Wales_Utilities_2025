package uk.ac.cf.spring.client_project.staff;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StaffServiceImpl implements StaffService {
    public HashMap<String, Object> stringToHashMap(String input) {
        input = input.substring(1, input.length() - 1);

        HashMap<String, Object> map = new HashMap<>();
        String[] splitInput = input.split(", ");

        for (String s : splitInput) {
            String[] splitItem = s.split("=", 2);
            String key = splitItem[0].trim();
            String value = splitItem[1].trim();

            map.put(key, value);        }
        return map;
    }
}
