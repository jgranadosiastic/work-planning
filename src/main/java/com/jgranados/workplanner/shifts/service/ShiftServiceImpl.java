package com.jgranados.workplanner.shifts.service;

import com.jgranados.workplanner.exception.InvalidShiftException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.shifts.dto.NewShiftDTO;
import com.jgranados.workplanner.shifts.domain.Shift;
import com.jgranados.workplanner.shifts.enums.ShiftHour;
import com.jgranados.workplanner.shifts.repository.ShiftRepository;
import com.jgranados.workplanner.workers.domain.Worker;
import com.jgranados.workplanner.workers.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.jgranados.workplanner.shifts.enums.ShiftHour.SHIFT_16_24;

@Service
public class ShiftServiceImpl implements ShiftService {

    public static final int MAX_NUMBER_OF_SHIFTS = 1;

    private WorkerService workerService;

    private ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImpl(WorkerService workerService, ShiftRepository shiftRepository) {
        this.workerService = workerService;
        this.shiftRepository = shiftRepository;
    }

    /**
     * Adds a new shift to a worker
     *
     * @param workerId
     * @param newShiftData
     * @return
     * @throws ResourceNotFoundException
     * @throws InvalidShiftException
     */
    @Override
    public Shift addShiftToWorker(int workerId, NewShiftDTO newShiftData) throws ResourceNotFoundException, InvalidShiftException {
        Worker worker = workerService.findById(workerId);
        LocalDateTime startDate = calculateShiftStartDate(newShiftData);
        int shiftCount = shiftRepository.countAllByWorkerAndStartDateBetween(
                worker,
                startDate,
                startDate.toLocalDate().atTime(LocalTime.MAX)
        );

        if (shiftCount >= MAX_NUMBER_OF_SHIFTS) {
            throw new InvalidShiftException("The worker cannot has two shifts on the same day.");
        }

        if (existsShift(worker, newShiftData)) {
            throw new InvalidShiftException("The worker already has a shift on the specified date and time.");
        }

        Shift newShift = new Shift();
        newShift.setWorker(worker);
        newShift.setStartDate(calculateShiftStartDate(newShiftData));
        newShift.setEndDate(calculateShiftEndDate(newShiftData));

        return shiftRepository.save(newShift);
    }

    /**
     * Checks if a shift already exists for worker on specified time
     *
     * @param worker
     * @param newShiftData
     * @return
     * @throws InvalidShiftException
     */
    @Override
    public boolean existsShift(Worker worker, NewShiftDTO newShiftData) {
        LocalDateTime shiftStartDate = calculateShiftStartDate(newShiftData);
        Optional<Shift> existingShift = shiftRepository.findByWorkerAndStartDate(worker, shiftStartDate);

        return existingShift.isPresent();
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void deleteById(int workerId, int shiftId) throws ResourceNotFoundException {
        workerService.findById(workerId);
        if (!existsById(shiftId)) {
            throw new ResourceNotFoundException("Cannot find shift with id: " + shiftId);
        }
        shiftRepository.deleteById(shiftId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Shift> findAllByWorkerAndDateRange(int workerId, LocalDate startDate, LocalDate endDate) throws ResourceNotFoundException {
        Worker worker = workerService.findById(workerId);
        if (startDate == null || endDate == null) {
            return shiftRepository.findAllByWorker(worker);
        }
        return shiftRepository.findAllByWorkerAndStartDateBetween(worker, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public boolean existsById(Integer id) {
        return shiftRepository.existsById(id);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Shift findById(int workerId, int shiftId) throws ResourceNotFoundException {
        workerService.findById(workerId);
        return shiftRepository
                .findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find shift with id: " + shiftId));
    }

    /**
     * Calculates the start date time for a shift
     *
     * @param newShiftData
     * @return
     */
    protected LocalDateTime calculateShiftStartDate(NewShiftDTO newShiftData) {
        ShiftHour shiftHour = newShiftData.getShiftHour();
        return newShiftData.getShiftDate().atTime(shiftHour.getStartHour(), 0);
    }

    /**
     * Calculates the end date time for a shift
     *
     * @param newShiftData
     * @return
     */
    protected LocalDateTime calculateShiftEndDate(NewShiftDTO newShiftData) {
        ShiftHour shiftHour = newShiftData.getShiftHour();
        if (SHIFT_16_24.equals(shiftHour)) { // the end date time is going to be at zero hour of next day
            return newShiftData.getShiftDate().plusDays(1).atStartOfDay();
        }

        return newShiftData.getShiftDate().atTime(shiftHour.getEndHour(), 0);
    }
}
