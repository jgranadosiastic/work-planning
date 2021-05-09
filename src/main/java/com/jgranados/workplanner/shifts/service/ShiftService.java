package com.jgranados.workplanner.shifts.service;

import com.jgranados.workplanner.exception.InvalidShiftException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.shifts.dto.NewShiftDTO;
import com.jgranados.workplanner.shifts.domain.Shift;
import com.jgranados.workplanner.workers.domain.Worker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ShiftService {

    /**
     * Adds a new shift to a worker
     *
     * @param workerId
     * @param newShiftData
     * @return
     * @throws ResourceNotFoundException
     * @throws InvalidShiftException
     */
    Shift addShiftToWorker(int workerId, NewShiftDTO newShiftData) throws ResourceNotFoundException, InvalidShiftException;

    /**
     * Checks if a shift already exists for worker at specified time
     *
     * @param worker
     * @param newShiftData
     * @return
     * @throws InvalidShiftException
     */
    boolean existsShift(Worker worker, NewShiftDTO newShiftData);

    /**
     * Removes a shift using its ID
     *
     * @param workerId
     * @param shiftId
     * @throws ResourceNotFoundException
     */
    void deleteById(int workerId, int shiftId) throws ResourceNotFoundException;

    /**
     * Finds all the shift for a worker in a date range. if startDate or endDate is null then it includes all the worker's shifts.
     *
     * @param workerId
     * @param startDate
     * @param endDate
     * @return
     * @throws ResourceNotFoundException
     */
    List<Shift> findAllByWorkerAndDateRange(int workerId, LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException;

    /**
     * Evaluates if a shift with id exists.
     *
     * @param id
     * @return
     */
    boolean existsById(Integer id);

    /**
     * Returns a shift based on the worker ID and shift id
     *
     * @param workerId the worker's ID
     * @param shiftId  the shift's ID
     * @return
     * @throws ResourceNotFoundException
     */
    Shift findById(int workerId, int shiftId) throws ResourceNotFoundException;

}
