package com.jgranados.workplanner.workers.service;

import com.jgranados.workplanner.workers.domain.Worker;
import com.jgranados.workplanner.exception.ResourceAlreadyExistsException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.workers.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;


    @Autowired
    public WorkerServiceImpl(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Worker create(Worker worker) throws ResourceAlreadyExistsException {
        if (worker.getIdWorker() != null && existsById(worker.getIdWorker())) {
            throw new ResourceAlreadyExistsException("Worker with id: " + worker.getIdWorker() + " already exists");
        }
        return workerRepository.save(worker);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Worker update(Worker worker) throws ResourceNotFoundException {
        if (!existsById(worker.getIdWorker())) {
            throw new ResourceNotFoundException("Cannot find Worker with id: " + worker.getIdWorker());
        }
        return workerRepository.save(worker);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void deleteById(int id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find Worker with id: " + id);
        }
        workerRepository.deleteById(id);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Worker findById(int id) throws ResourceNotFoundException {
        return workerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Worker with id: " + id));
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Worker> findAllByFirstNameAndLastName(String firstName, String lastName) {
        return workerRepository.findAllByFirstNameLikeAndLastNameLike("%" + firstName + "%", "%" + lastName + "%");
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public boolean existsById(int id) {
        return workerRepository.existsById(id);
    }


}
