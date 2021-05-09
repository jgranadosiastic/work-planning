package com.jgranados.workplanner.healthcheck;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/health-check")
@RestController
public class HelathCheckController {

    @GetMapping
    public ResponseEntity<Void> checkApp() {
        return ResponseEntity
                .ok()
                .build();
    }
}
