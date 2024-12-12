package uk.ac.cf.spring.client_project.visit;

import java.util.List;
import java.util.Map;

public interface VisitRepository {
    List<Map<String, Object>> getCurrentlyOnSiteVisits();
    void save(VisitDTO visit);

    void update(VisitDTO visit);

    VisitDTO getCurrentVisit(Long userId, Long locationId);
}
