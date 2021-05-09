package com.jgranados.workplanner.workers.api;

import com.jgranados.workplanner.exception.ResourceAlreadyExistsException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.workers.domain.Worker;
import com.jgranados.workplanner.workers.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "api/v1/workers",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class WorkerController {

    private WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Worker> create(@Valid @RequestBody Worker worker) throws ResourceAlreadyExistsException {
        Worker newWorker = workerService.create(worker);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newWorker.getIdWorker())
                .toUri();
        return ResponseEntity.created(location).body(newWorker);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Worker> update(@Valid @RequestBody Worker worker, @PathVariable int id)
            throws ResourceNotFoundException {

        worker.setIdWorker(id);
        workerService.update(worker);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws ResourceNotFoundException {

        workerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(workerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Worker>> findAllByFirstNameAndLastName(
            @RequestParam(required = false, defaultValue = "") String firstName,
            @RequestParam(required = false, defaultValue = "") String lastName
    ) {
        return ResponseEntity.ok(workerService.findAllByFirstNameAndLastName(firstName, lastName));
    }
}
