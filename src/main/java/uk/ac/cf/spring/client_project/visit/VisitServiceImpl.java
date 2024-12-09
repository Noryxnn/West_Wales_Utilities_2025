package uk.ac.cf.spring.client_project.visit;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Map<String, Object>> getCurrentlyOnSiteVisits() {
        return visitRepository.getCurrentlyOnSiteVisits();
    }
}
