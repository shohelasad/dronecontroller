package com.app.demo.dto;

import com.app.demo.enums.Direction;
import jakarta.validation.constraints.Min;

public record DroneDto(Long id, @Min(value = 0, message = "Value must be greater than or equal to 0") int x, @Min(value = 0, message = "Value must be greater than or equal to 0") int y, Direction direction) {
}