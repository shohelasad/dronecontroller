package com.app.demo;

import com.app.demo.dto.DroneDto;
import com.app.demo.entity.Drone;
import com.app.demo.enums.Direction;
import com.app.demo.exception.BadRequestException;
import com.app.demo.exception.ResourceNotFoundException;
import com.app.demo.repository.DroneRepository;
import com.app.demo.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    private DroneService droneService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        droneService = new DroneService(10, 10, droneRepository);
    }

    @Test
    public void testRegisterDroneWithValidLocation() {
        DroneDto droneDto = new DroneDto(null, 0, 0, Direction.NORTH);
        Drone drone = new Drone(0, 0, Direction.NORTH);

        when(droneRepository.save(drone)).thenReturn(drone);

        DroneDto result = droneService.registerDrone(droneDto);

        assertEquals(droneDto, result);
    }

    @Test
    public void testRegisterDroneWithInvalidLocation() {
        DroneDto droneDto = new DroneDto(null, 11, 2, Direction.NORTH);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> droneService.registerDrone(droneDto));

        assertEquals("Drone initialized location is not valid!", exception.getMessage());
    }

    @Test
    public void testUnregisterDrone() {
        Long droneId = 1L;

        droneService.unregisterDrone(droneId);

        verify(droneRepository, times(1)).deleteById(droneId);
    }

    @Test
    public void testGetCurrentLocation() {
        Long droneId = 1L;
        Drone drone = new Drone(1, 2, Direction.EAST);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        String result = droneService.getCurrentLocation(droneId);

        assertEquals("[1, 2]", result);
    }

    @Test
    public void testGetCurrentLocationDroneNotFound() {
        Long droneId = 1L;

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> droneService.getCurrentLocation(droneId));
    }

    @Test
    public void testTurnDroneLeft() {
        Long droneId = 1L;
        Drone drone = new Drone(1, 2, Direction.NORTH);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        String result = droneService.turnDrone(droneId, "left");

        assertEquals("I am on [1, 2] watching WEST", result);
    }

    @Test
    public void testTurnDroneRight() {
        Long droneId = 1L;
        Drone drone = new Drone(1, 2, Direction.NORTH);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        String result = droneService.turnDrone(droneId, "right");

        assertEquals("I am on [1, 2] watching EAST", result);
    }

    @Test
    public void testGoForward() {
        Long droneId = 1L;
        Drone drone = new Drone(1, 2, Direction.NORTH);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        String result = droneService.goForward(droneId);

        assertEquals("Now I am at [1, 3]", result);
    }

    @Test
    public void testGoForwardCannotMove() {
        Long droneId = 1L;
        Drone drone = new Drone(0, 9, Direction.NORTH);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        String result = droneService.goForward(droneId);

        assertEquals("Cannot do it [0, 9]", result);
    }
}
