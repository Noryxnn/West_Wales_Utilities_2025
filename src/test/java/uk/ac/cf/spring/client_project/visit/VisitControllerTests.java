package uk.ac.cf.spring.client_project.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        VisitDTO mockVisit = new VisitDTO("John", "Office", "2024-12-04T10:00:00");
        when(visitService.getCurrentlyOnSiteVisits()).thenReturn(List.of(mockVisit));

        ModelAndView result = visitController.getAllVisits();

        assertEquals("visit/visittracking", result.getViewName());
        assertEquals(1, ((List<?>) result.getModel().get("visits")).size());
    }
}
