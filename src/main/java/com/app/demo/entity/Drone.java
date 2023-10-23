package com.app.demo.entity;

import com.app.demo.enums.Direction;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    private int x;
    private int y;
    private Direction direction;

    public Drone() {
        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
    }

    public Drone(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
