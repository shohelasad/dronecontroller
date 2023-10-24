package com.app.demo;

import com.app.demo.controller.DroneController;
import com.app.demo.dto.DroneDto;
import com.app.demo.enums.Direction;
import com.app.demo.service.DroneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DroneController.class)
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterDrone() throws Exception {
        DroneDto droneDto = new DroneDto(null, 1, 2, Direction.NORTH);

        when(droneService.registerDrone(any(DroneDto.class))).thenReturn(droneDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(droneDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.x").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.y").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direction").value("NORTH"));
    }

    @Test
    public void testUnregisterDrone() throws Exception {
        Long droneId = 1l;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/drones/{droneId}", droneId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(droneService, times(1)).unregisterDrone(1L);
    }

    @Test
    public void testWhereAreYou() throws Exception {
        Long droneId = 1l;
        when(droneService.getCurrentLocation(any(Long.class))).thenReturn("[2, 3]");

        mockMvc.perform(get("/api/v1/drones/{droneId}/where-are-you", droneId))
                .andExpect(status().isOk())
                .andExpect(content().string("[2, 3]"));
    }

    @Test
    public void testTurnLeft() throws Exception {
        Long droneId = 1l;
        when(droneService.turnDrone(1l,"left")).thenReturn("I am on [2, 3] watching North");

        mockMvc.perform(put("/api/v1/drones/{droneId}/turn", droneId)
                        .param("direction", "left"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am on [2, 3] watching North"));
    }

    @Test
    public void testTurnRight() throws Exception {
        Long droneId = 1l;
        when(droneService.turnDrone(1l,"right")).thenReturn("I am on [2, 3] watching West");

        mockMvc.perform(put("/api/v1/drones/{droneId}/turn", droneId)
                        .param("direction", "right"))
                .andExpect(status().isOk())
                .andExpect(content().string("I am on [2, 3] watching West"));
    }

    @Test
    public void testMoveForward() throws Exception {
        Long droneId = 1l;
        when(droneService.goForward(1l)).thenReturn("Now I am at [2,3]");

        mockMvc.perform(put("/api/v1/drones/{droneId}/go-forward", droneId))
                .andExpect(status().isOk())
                .andExpect(content().string("Now I am at [2,3]"));
    }
}
