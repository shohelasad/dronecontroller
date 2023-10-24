package com.app.demo.dto;

import com.app.demo.enums.Direction;

public record DroneDto(Long id, int x, int y, Direction direction) {
}