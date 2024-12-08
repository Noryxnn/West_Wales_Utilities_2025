package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StaffServiceTests {
    @Autowired
    StaffService staffService;
    @MockBean
    RequestRepository requestRepository;

    @Test
    void shouldConvertStringToHashMap() {
        String input = "{secretKey=123=, userId=1, timestamp=2024-12-04T19:31:02.653446300Z}";

        HashMap<String, Object> output = staffService.stringToHashMap(input);

        assertEquals(3, output.size());
        assertEquals("1", output.get("userId"));
        assertEquals("2024-12-04T19:31:02.653446300Z", output.get("timestamp"));
        assertEquals("123=", output.get("secretKey"));
    }

    @Test
    void shouldReturnTrueForApprovedVisitor() {
        Long userId = 1L;
        List<Request> requests = new ArrayList<>();
        Request approvedRequest = new Request();
        approvedRequest.setApproved(true);
        approvedRequest.setVisitStartDate(LocalDate.now());
        approvedRequest.setVisitEndDate(LocalDate.now().plusDays(7));
        requests.add(approvedRequest);

        when(requestRepository.findByUserId(userId)).thenReturn(requests);

        assertTrue(staffService.isVisitorApproved(userId));
    }

    @Test
    void shouldReturnFalseForUnapprovedVisitor() {
        Long userId = 1L;
        List<Request> requests = new ArrayList<>();
        Request unapprovedRequest = new Request();
        unapprovedRequest.setApproved(false);
        unapprovedRequest.setVisitStartDate(LocalDate.now());
        unapprovedRequest.setVisitEndDate(LocalDate.now().plusDays(7));
        requests.add(unapprovedRequest);

        when(requestRepository.findByUserId(userId)).thenReturn(requests);

        assertFalse(staffService.isVisitorApproved(userId));
    }
}
