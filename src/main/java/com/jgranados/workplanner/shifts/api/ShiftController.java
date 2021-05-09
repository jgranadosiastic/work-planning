package com.jgranados.workplanner.shifts.api;

import com.jgranados.workplanner.exception.InvalidShiftException;
import com.jgranados.workplanner.exception.ResourceAlreadyExistsException;
import com.jgranados.workplanner.exception.ResourceNotFoundException;
import com.jgranados.workplanner.shifts.domain.Shift;
import com.jgranados.workplanner.shifts.dto.NewShiftDTO;
import com.jgranados.workplanner.shifts.service.ShiftService;
import com.jgranados.workplanner.workers.domain.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
        value = "api/v1/workers/{workerId}/shifts",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ShiftController {

    private ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Shift> addShiftToWorker(@PathVariable int workerId,
                                                  @Valid @RequestBody NewShiftDTO newShiftData)
            throws ResourceNotFoundException, InvalidShiftException {
        Shift newShift = shiftService.addShiftToWorker(workerId, newShiftData);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newShift.getIdShift())
                .toUri();
        return ResponseEntity.created(location).body(newShift);
    }

    @GetMapping(value = "/{shiftId}")
    public ResponseEntity<Shift> findById(@PathVariable int workerId, @PathVariable int shiftId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(shiftService.findById(workerId, shiftId));
    }

    @GetMapping
    public ResponseEntity<List<Shift>> findAllByWorkerAndDateRange(
            @PathVariable int workerId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(shiftService.findAllByWorkerAndDateRange(workerId, startDate, endDate));
    }

    @DeleteMapping(value = "/{shiftId}")
    public ResponseEntity<Void> delete(@PathVariable int workerId, @PathVariable int shiftId)
            throws ResourceNotFoundException {

        shiftService.deleteById(workerId, shiftId);
        return ResponseEntity.ok().build();
    }
}
