package com.app.demo.utils;

import com.app.demo.entity.Drone;
import com.app.demo.enums.Direction;

import java.util.HashMap;
import java.util.Map;

public class DroneUtils {
    private static final Map<Direction, Direction> LEFT_TURN_MAP = new HashMap<>();
    private static final Map<Direction, Direction> RIGHT_TURN_MAP = new HashMap<>();

    static {
        LEFT_TURN_MAP.put(Direction.NORTH, Direction.WEST);
        LEFT_TURN_MAP.put(Direction.WEST, Direction.SOUTH);
        LEFT_TURN_MAP.put(Direction.SOUTH, Direction.EAST);
        LEFT_TURN_MAP.put(Direction.EAST, Direction.NORTH);

        RIGHT_TURN_MAP.put(Direction.NORTH, Direction.EAST);
        RIGHT_TURN_MAP.put(Direction.EAST, Direction.SOUTH);
        RIGHT_TURN_MAP.put(Direction.SOUTH, Direction.WEST);
        RIGHT_TURN_MAP.put(Direction.WEST, Direction.NORTH);
    }

    public static void turnLeft(Drone drone) {
        drone.setDirection(LEFT_TURN_MAP.get(drone.getDirection()));
    }

    public static void turnRight(Drone drone) {
        drone.setDirection(RIGHT_TURN_MAP.get(drone.getDirection()));
    }

    public static void goForward(Drone drone) {
        int x = drone.getX();
        int y = drone.getY();
        switch (drone.getDirection()) {
            case NORTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y--;
                break;
            case WEST:
                x--;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

        drone.setX(x);
        drone.setY(y);
    }
}