package uk.ac.cf.spring.client_project.visit;


import java.util.List;
import java.util.Map;

public interface VisitService {
    List<Map<String, Object>> getCurrentlyOnSiteVisits();
    void save(VisitDTO visitDTO);
}