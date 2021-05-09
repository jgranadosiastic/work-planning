package com.jgranados.workplanner.workers.service;

import com.jgranados.workplanner.exception.ResourceAlreadyExistsException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.workers.domain.Worker;

import java.util.List;

public interface WorkerService {

    /**
     * Creates a new worker
     *
     * @param worker
     * @return
     * @throws ResourceAlreadyExistsException
     */
    Worker create(Worker worker) throws ResourceAlreadyExistsException;

    /**
     * Updates a new worker
     *
     * @param worker
     * @return
     * @throws ResourceAlreadyExistsException
     */
    Worker update(Worker worker) throws ResourceNotFoundException;

    /**
     * Removes a worker using its ID
     *
     * @param id
     * @throws ResourceNotFoundException
     */
    void deleteById(int id) throws ResourceNotFoundException;

    /**
     * Returns a worker based on the ID
     *
     * @param id the worker's ID
     * @return
     * @throws ResourceNotFoundException
     */
    Worker findById(int id) throws ResourceNotFoundException;

    /**
     * Returns all the workers filtering by first name and last name
     *
     * @return
     */
    List<Worker> findAllByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Evaluates if a worker with id exists.
     *
     * @param id
     * @return
     */
    boolean existsById(int id);
}
