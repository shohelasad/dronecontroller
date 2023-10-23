package com.app.demo.controller;

import com.app.demo.dto.DroneDto;
import com.app.demo.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<DroneDto> registerDrone(@RequestBody DroneDto drone) {
        log.info("Registering a new drone: {}", drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneService.registerDrone(drone));
    }

    @DeleteMapping("/{droneId}")
    public ResponseEntity<String> unregisterDrone(@PathVariable Long droneId) {
        log.info("Unregistering a drone with droneId: " + droneId);
        droneService.unregisterDrone(droneId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{droneId}/where-are-you")
    public ResponseEntity<String> getCurrentLocation(@PathVariable Long droneId) {
        log.info("Fetching location of droneId: " + droneId);
        String response = droneService.getCurrentLocation(droneId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{droneId}/turn")
    public ResponseEntity<String> turnDrone(@PathVariable Long droneId, @RequestParam String direction) {
        log.info("Turning drone id: " + droneId + " to ", direction);
        String response = droneService.turnDrone(droneId, direction);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{droneId}/go-forward")
    public ResponseEntity<String> goForward(@PathVariable Long droneId) {
        log.info("Moving forward droneId: " + droneId);
        String response = droneService.goForward(droneId);
        return ResponseEntity.ok(response);
    }
}
