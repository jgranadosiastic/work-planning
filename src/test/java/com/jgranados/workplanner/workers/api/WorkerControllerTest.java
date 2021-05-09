package com.jgranados.workplanner.workers.api;

import com.jgranados.workplanner.workers.domain.Worker;
import com.jgranados.workplanner.workers.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class WorkerControllerTest {

    @InjectMocks
    WorkerController workerController;

    @Mock
    WorkerService workerService;

    @Test
    void whenValidDataThenCreateWorker() throws Exception {
        // Arrange
        Worker newWorker = new Worker();
        newWorker.setIdWorker(1);
        newWorker.setFirstName("Harry");
        newWorker.setLastName("Potter");

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(workerService.create(any(Worker.class))).thenReturn(newWorker);

        // Act
        ResponseEntity<Worker> responseEntity = workerController.create(newWorker);

        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertTrue(responseEntity.getHeaders().getLocation().getPath().contains("/1"))
        );
    }

    @Test
    void whenWorkerExistsThenDeleteWorker() throws Exception {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Act
        ResponseEntity<Void> responseEntity = workerController.delete(1);

        // Arrange
        // Assert
        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );

    }
}