package com.jgranados.workplanner.workers.repository;

import com.jgranados.workplanner.workers.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    /**
     * Find all the workers by first name and last name
     *
     * @param firstName
     * @param lastName
     * @return
     */
    List<Worker> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
