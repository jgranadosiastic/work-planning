package com.jgranados.workplanner.shifts.service;

import com.jgranados.workplanner.exception.InvalidShiftException;
import com.jgranados.workplanner.shifts.domain.Shift;
import com.jgranados.workplanner.shifts.dto.NewShiftDTO;
import com.jgranados.workplanner.shifts.repository.ShiftRepository;
import com.jgranados.workplanner.workers.domain.Worker;
import com.jgranados.workplanner.workers.service.WorkerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.jgranados.workplanner.shifts.enums.ShiftHour.SHIFT_0_8;
import static com.jgranados.workplanner.shifts.enums.ShiftHour.SHIFT_16_24;
import static com.jgranados.workplanner.shifts.service.ShiftServiceImpl.MAX_NUMBER_OF_SHIFTS;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ShiftServiceImplTest {

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private WorkerService workerService;

    @InjectMocks
    private ShiftServiceImpl shiftService;

    @Test
    void whenValidDataThenAddShiftToWorker() throws Exception {
        // Arrange
        NewShiftDTO newShiftDTO = new NewShiftDTO();
        newShiftDTO.setShiftDate(LocalDate.now());
        newShiftDTO.setShiftHour(SHIFT_0_8);

        Worker harry = new Worker();
        harry.setIdWorker(1);

        Mockito.when(workerService.findById(harry.getIdWorker()))
                .thenReturn(harry);

        Mockito.when(shiftRepository.countAllByWorkerAndStartDateBetween(any(Worker.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0);

        Shift newShift = new Shift();
        newShift.setStartDate(shiftService.calculateShiftStartDate(newShiftDTO));
        newShift.setEndDate(shiftService.calculateShiftEndDate(newShiftDTO));
        Mockito.when(shiftRepository.save(any(Shift.class))).thenReturn(newShift);

        // Act
        Shift result = shiftService.addShiftToWorker(1, newShiftDTO);

        // Assert
        assertAll(
                () -> assertEquals(LocalDate.now().atTime(SHIFT_0_8.getStartHour(), 0), result.getStartDate()),
                () -> assertEquals(LocalDate.now().atTime(SHIFT_0_8.getEndHour(), 0), result.getEndDate())
        );
    }

    @Test
    void whenWorkerHasShiftOnDayThenThrowsException() throws Exception {
        // Arrange
        NewShiftDTO newShiftDTO = new NewShiftDTO();
        newShiftDTO.setShiftDate(LocalDate.now());
        newShiftDTO.setShiftHour(SHIFT_0_8);

        Worker harry = new Worker();
        harry.setIdWorker(1);

        Mockito.when(workerService.findById(harry.getIdWorker()))
                .thenReturn(harry);

        Mockito.when(shiftRepository.countAllByWorkerAndStartDateBetween(any(Worker.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(MAX_NUMBER_OF_SHIFTS);

        // Act
        Assertions.assertThrows(InvalidShiftException.class,
                () -> shiftService.addShiftToWorker(1, newShiftDTO)
        );
    }

    @Test
    void whenShiftStartsAt0HourThenCalculateShiftEndDate() {
        // Arrange
        NewShiftDTO newShiftDTO = new NewShiftDTO();
        newShiftDTO.setShiftDate(LocalDate.now());
        newShiftDTO.setShiftHour(SHIFT_0_8);

        // Act
        LocalDateTime result = shiftService.calculateShiftEndDate(newShiftDTO);

        // Assert
        assertEquals(LocalDate.now().atTime(SHIFT_0_8.getEndHour(), 0), result);

    }

    @Test
    void whenShiftStartsAt16HourThenCalculateShiftEndDate() {
        // Arrange
        NewShiftDTO newShiftDTO = new NewShiftDTO();
        newShiftDTO.setShiftDate(LocalDate.now());
        newShiftDTO.setShiftHour(SHIFT_16_24);

        // Act
        LocalDateTime result = shiftService.calculateShiftEndDate(newShiftDTO);

        // Assert
        assertEquals(LocalDate.now().plusDays(1).atStartOfDay(), result);

    }
}