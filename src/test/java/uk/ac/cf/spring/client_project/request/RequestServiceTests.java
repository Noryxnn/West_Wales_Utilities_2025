package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RequestServiceTests {
    @MockBean
    private RequestRepository requestRepository;
    @Autowired
    private RequestService requestService;

    @Test
    public void shouldSaveNewRequest() {
        // Create a new request
        Request request = new Request(0L, 1L, 2L, LocalDate.now(), LocalDate.of(2024, 12, 1));
        requestService.save(request);
        // Verify that the save method was called exactly once with the request object.
        // sourced from https://www.baeldung.com/mockito-verify
        verify(requestRepository, times(1)).save(request);
    }

    @Test
    public void shouldFetchOpenRequests() {
        List<Request> mockRequests = List.of(
                new Request(1L, 1L, 2L, LocalDate.now(), LocalDate.of(2024, 12, 1))
        );
        when(requestRepository.getOpenRequests()).thenReturn(mockRequests);
        List<Request> requests = requestService.getOpenRequests();
        assertEquals(1, requests.size());
        assertEquals(1L, requests.get(0).getRequestId());
    }
}
