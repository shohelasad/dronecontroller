package com.app.demo.entity;

import com.app.demo.enums.Direction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @NotNull
    private Integer x;
    @NotNull
    private Integer y;
    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Drone() {
        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
    }

    public Drone(Integer x, Integer y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
