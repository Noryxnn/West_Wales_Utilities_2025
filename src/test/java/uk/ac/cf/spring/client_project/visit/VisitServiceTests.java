package uk.ac.cf.spring.client_project.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VisitServiceTests {
    private VisitService visitService;
    private VisitRepository visitRepository;

    @BeforeEach
    void setUp() {
        visitRepository = mock(VisitRepository.class);
        visitService = new VisitService(visitRepository);
    }

    @Test
    void testGetCurrentlyOnSiteVisits() {
        VisitDTO mockVisit = new VisitDTO("John", "Office", "2024-12-04T10:00:00");
        when(visitRepository.findCurrentlyOnSiteVisits()).thenReturn(List.of(mockVisit));

        List<VisitDTO> result = visitService.getCurrentlyOnSiteVisits();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getUserName());
    }
}
