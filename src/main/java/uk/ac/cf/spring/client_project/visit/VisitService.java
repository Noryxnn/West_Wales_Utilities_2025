package uk.ac.cf.spring.client_project.visit;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<VisitDTO> getCurrentlyOnSiteVisits() {
        return visitRepository.findCurrentlyOnSiteVisits();
    }

    public void addVisit(VisitDTO visitDTO) {
        // Logic to map VisitDTO to an entity or direct insertion
        visitRepository.saveVisit(visitDTO);
    }
}