package com.app.demo.service;

import com.app.demo.dto.DroneDto;
import com.app.demo.entity.Drone;
import com.app.demo.exception.BadRequestException;
import com.app.demo.exception.ResourceNotFoundException;
import com.app.demo.repository.DroneRepository;
import com.app.demo.utils.DroneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DroneService {
    private final int maxX;
    private final int maxY;

    private final DroneRepository droneRepository;

    public DroneService(@Value("${field.x}") int maxX,  @Value("${field.y}") int maxY,
                        DroneRepository droneRepository) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.droneRepository = droneRepository;
    }

    public DroneDto registerDrone(DroneDto droneDto) {
        if(droneDto.getX() >= maxX || droneDto.getX() < 0 || droneDto.getY() >= maxY || droneDto.getY() < 0) {
            throw new BadRequestException("Drone initialized location is not valid!");
        }
        Drone drone = new Drone(droneDto.getX(), droneDto.getY(), droneDto.getDirection());
        Drone registered = droneRepository.save(drone);
        log.info("Registered drone: {}", registered);
        return convertToDto(registered);
    }

    public void unregisterDrone(Long droneId) {
        droneRepository.deleteById(droneId);
    }

    public String getCurrentLocation(Long droneId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found: " + droneId));
        log.info("Fetching location of drone: {}", drone);
        return getDroneLocation(drone);
    }

    public String turnDrone(Long droneId, String turnDirection) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found: " + droneId));
        if (turnDirection.equals("left")) {
            DroneUtils.turnLeft(drone);
        } else if (turnDirection.equals("right")) {
            DroneUtils.turnRight(drone);
        }
        droneRepository.save(drone);
        log.info("Turned drone: {}", drone);
        return getDroneStatus(drone);
    }

    public String goForward(Long droneId) {
        Drone drone = droneRepository.findById(droneId)
                .orElseThrow(() -> new ResourceNotFoundException("Drone not found: " + droneId));
        if (!canGoForward(drone)) {
            return "Cannot do it [" + drone.getX() + ", " + drone.getY() + "]";
        }
        DroneUtils.goForward(drone);
        droneRepository.save(drone);
        log.info("Moved forward drone: {}", drone);
        return "Now I am at " + getDroneLocation(drone);
    }

    private String getDroneStatus(Drone drone) {
        return "I am on " + getDroneLocation(drone) + " watching " + drone.getDirection();
    }

    private String getDroneLocation(Drone drone) {
        return "[" + drone.getX() + ", " + drone.getY() + "]";
    }

    private boolean canGoForward(Drone drone) {
        return switch (drone.getDirection()) {
            case NORTH -> drone.getY() < maxY - 1;
            case EAST -> drone.getX() < maxX - 1;
            case SOUTH -> drone.getY() > 0;
            case WEST -> drone.getX() > 0;
        };
    }

    private DroneDto convertToDto(Drone drone) {
        return new DroneDto(drone.getId(), drone.getX(), drone.getY(), drone.getDirection());
    }
}