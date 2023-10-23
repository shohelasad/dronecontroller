package com.app.demo.dto;

import com.app.demo.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {
    private Long id;
    private int x;
    private int y;
    private Direction direction;
}