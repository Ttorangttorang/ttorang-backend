package com.ttorang.api.healthcheck;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/health-check")
@Tag(name = "health", description = "try health-check")
public class HealthCheckController {

    @GetMapping("")
    public ResponseEntity healthCheck() {
        log.info("health-check success");
        return ResponseEntity.ok("health-check success");
    }

}
