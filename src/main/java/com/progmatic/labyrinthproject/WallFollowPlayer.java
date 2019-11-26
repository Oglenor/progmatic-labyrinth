package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;

public class WallFollowPlayer implements Player {
    private Direction lastDirection;
    private static int stepCounter = 0;
    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleDirections = l.possibleMoves();
        stepCounter++;
        if (stepCounter < 2) {
            lastDirection = Direction.EAST;
            return Direction.EAST;
        }

        else {
            if (lastDirection.equals(Direction.EAST)) {
                Direction[] actualDirectionOrder = {Direction.SOUTH, Direction.EAST, Direction.NORTH, Direction.WEST};
                for (int i = 0; i < actualDirectionOrder.length; i++) {
                    if (possibleDirections.contains(actualDirectionOrder[i])) {
                        lastDirection = actualDirectionOrder[i];
                        return actualDirectionOrder[i];
                    }
                }
            }
            if (lastDirection.equals(Direction.SOUTH)) {
                Direction[] actualDirectionOrder = {Direction.WEST, Direction.SOUTH, Direction.EAST, Direction.NORTH};
                for (int i = 0; i < actualDirectionOrder.length; i++) {
                    if (possibleDirections.contains(actualDirectionOrder[i])) {
                        lastDirection = actualDirectionOrder[i];
                        return actualDirectionOrder[i];
                    }
                }
            }
            if (lastDirection.equals(Direction.WEST)) {
                Direction[] actualDirectionOrder = {Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST};
                for (int i = 0; i < actualDirectionOrder.length; i++) {
                    if (possibleDirections.contains(actualDirectionOrder[i])) {
                        lastDirection = actualDirectionOrder[i];
                        return actualDirectionOrder[i];
                    }
                }
            }
            if (lastDirection.equals(Direction.NORTH)) {
                Direction[] actualDirectionOrder = {Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH};
                for (int i = 0; i < actualDirectionOrder.length; i++) {
                    if (possibleDirections.contains(actualDirectionOrder[i])) {
                        lastDirection = actualDirectionOrder[i];
                        return actualDirectionOrder[i];
                    }
                }
            }
        }
        return null;
    }
}
