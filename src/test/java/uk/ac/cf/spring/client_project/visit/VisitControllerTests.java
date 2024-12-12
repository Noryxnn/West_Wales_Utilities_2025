package uk.ac.cf.spring.client_project.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VisitControllerTests {
    private VisitController visitController;
    private VisitService visitService;

    @BeforeEach
    void setUp() {
        visitService = mock(VisitService.class);
        visitController = new VisitController(visitService);
    }

    @Test
    void testGetAllVisits() {
        List<Map<String, Object>> mockVisits = List.of(Map.of("visitorName", "John Doe", "locationName", "Office", "checkInDate", "04/12/2024", "checkInTime", "00:00"));
        when(visitService.getCurrentlyOnSiteVisits()).thenReturn(mockVisits);

        ModelAndView result = visitController.getAllVisits();

        assertEquals("visit/visit-tracking", result.getViewName());
        assertEquals(1, ((List<?>) result.getModel().get("visits")).size());
    }
}
