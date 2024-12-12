package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestRepository;
import uk.ac.cf.spring.client_project.request.RequestStatus;

import java.time.LocalDate;
import java.util.ArrayList;
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
    void shouldReturnTrueForApprovedVisitor() {
        Long userId = 1L;
        List<Request> requests = new ArrayList<>();
        Request approvedRequest = new Request();
        approvedRequest.setStatus(RequestStatus.APPROVED);
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
        unapprovedRequest.setStatus(RequestStatus.DENIED);
        unapprovedRequest.setVisitStartDate(LocalDate.now());
        unapprovedRequest.setVisitEndDate(LocalDate.now().plusDays(7));
        requests.add(unapprovedRequest);

        when(requestRepository.findByUserId(userId)).thenReturn(requests);

        assertFalse(staffService.isVisitorApproved(userId));
    }
}
