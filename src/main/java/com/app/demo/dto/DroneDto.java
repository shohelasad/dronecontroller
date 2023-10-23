package com.app.demo.dto;

import com.app.demo.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {
    private Long id;
    private int x;
    private int y;
    private Direction direction;
}
