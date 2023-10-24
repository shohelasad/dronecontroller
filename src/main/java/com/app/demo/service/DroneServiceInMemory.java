package com.app.demo.service;

import com.app.demo.dto.DroneDto;
import com.app.demo.entity.Drone;
import com.app.demo.exception.BadRequestException;
import com.app.demo.exception.ResourceNotFoundException;
import com.app.demo.utils.DroneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * this is sample dummy code to explain in memory HashMap (ConcurrentHashMap) based solution
 */


@Slf4j
@Service
public class DroneServiceInMemory {
    private final int maxX;
    private final int maxY;
    private final Map<Long, Drone> droneMap;
    private final AtomicLong nextDroneId = new AtomicLong(1);

    public DroneServiceInMemory(@Value("${field.x}") int maxX, @Value("${field.y}") int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.droneMap = new ConcurrentHashMap<>();
    }

    public DroneDto registerDrone(DroneDto droneDto) {
        if (droneDto.x() >= maxX || droneDto.x() < 0 || droneDto.y() >= maxY || droneDto.y() < 0) {
            throw new BadRequestException("Drone initialized location is not valid!");
        }
        long droneId = nextDroneId.getAndIncrement();
        Drone drone = new Drone(droneId, droneDto.x(), droneDto.y(), droneDto.direction());
        droneMap.put(droneId, drone);
        log.info("Registered drone: {}", drone);
        return convertToDto(drone);
    }

    public void unregisterDrone(Long droneId) {
        droneMap.remove(droneId);
    }

    public String getCurrentLocation(Long droneId) {
        Drone drone = (Drone) droneMap.get(droneId);
        if (drone == null) {
            throw new ResourceNotFoundException("Drone not found: " + droneId);
        }
        log.info("Fetching location of drone: {}", drone);
        return getDroneLocation(drone);
    }

    public String turnDrone(Long droneId, String turnDirection) {
        Drone drone = (Drone) droneMap.get(droneId);
        if (drone == null) {
            throw new ResourceNotFoundException("Drone not found: " + droneId);
        }

        if (turnDirection.equals("left")) {
            DroneUtils.turnLeft(drone);
        } else if (turnDirection.equals("right")) {
            DroneUtils.turnRight(drone);
        }
        log.info("Turned drone: {}", drone);
        return getDroneStatus(drone);
    }

    public String goForward(Long droneId) {
        Drone drone = (Drone) droneMap.get(droneId);
        if (drone == null) {
            throw new ResourceNotFoundException("Drone not found: " + droneId);
        }

        if (!DroneUtils.canGoForward(drone, maxX, maxY)) {
            return "Cannot do it [" + drone.getX() + ", " + drone.getY() + "]";
        }
        DroneUtils.goForward(drone);
        log.info("Moved forward drone: {}", drone);
        return "Now I am at " + getDroneLocation(drone);
    }

    private String getDroneStatus(Drone drone) {
        return "I am on " + getDroneLocation(drone) + " watching " + drone.getDirection();
    }

    private String getDroneLocation(Drone drone) {
        return "[" + drone.getX() + ", " + drone.getY() + "]";
    }

    private DroneDto convertToDto(Drone drone) {
        return new DroneDto(drone.getId(), drone.getX(), drone.getY(), drone.getDirection());
    }
}
