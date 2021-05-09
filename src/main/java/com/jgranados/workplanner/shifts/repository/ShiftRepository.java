package com.jgranados.workplanner.shifts.repository;

import com.jgranados.workplanner.shifts.domain.Shift;
import com.jgranados.workplanner.workers.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

    /**
     * Finds all the shifts owned by a worker
     *
     * @param worker
     * @return
     */
    List<Shift> findAllByWorker(Worker worker);

    /**
     * Finds
     *
     * @param worker
     * @param start
     * @param end
     * @return
     */
    List<Shift> findAllByWorkerAndStartDateBetween(Worker worker, LocalDateTime start, LocalDateTime end);

    /**
     * Finds a shift owned by a worker on the start date.
     *
     * @param worker
     * @param start
     * @return
     */
    Optional<Shift> findByWorkerAndStartDate(Worker worker, LocalDateTime start);

    /**
     * Counts all the shifts owned by a worker and defined in the date range
     *
     * @param worker
     * @param start
     * @param end
     * @return
     */
    int countAllByWorkerAndStartDateBetween(Worker worker, LocalDateTime start, LocalDateTime end);

}
