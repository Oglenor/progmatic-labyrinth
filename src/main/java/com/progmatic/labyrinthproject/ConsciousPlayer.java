package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.ArrayList;

public class ConsciousPlayer implements Player {
    //ArrayList<Coordinate>  coordinateList;
    private static int counter = 0;

    @Override
    public Direction nextMove(Labyrinth l) {
        ArrayList<Coordinate> coordinateList = findRoute(l);
        Coordinate actCoord = coordinateList.get(counter);
        counter++;
        if (actCoord.getRow()-l.getPlayerPosition().getRow() == 0 && actCoord.getCol()-l.getPlayerPosition().getCol() == 1){
            return Direction.EAST;
        }
        if (actCoord.getRow()-l.getPlayerPosition().getRow() == 0 && actCoord.getCol()-l.getPlayerPosition().getCol() == -1){
            return Direction.WEST;
        }

         if (actCoord.getRow()-l.getPlayerPosition().getRow() == 1 && actCoord.getCol()-l.getPlayerPosition().getCol() == 0){
             return Direction.SOUTH;
         }
        if (actCoord.getRow()-l.getPlayerPosition().getRow() == -1 && actCoord.getCol()-l.getPlayerPosition().getCol() == 0){
            return Direction.NORTH;
        }
        return null;
    }

    public ArrayList<Coordinate> findRoute(Labyrinth l){
        if (l instanceof LabyrinthImpl){
            ArrayList<Coordinate> result = new ArrayList<>();
            return findRoute(result,((LabyrinthImpl) l).getMap(),mapLoader(((LabyrinthImpl) l).getMap()),1,1, false);
        }
        else{
            return null;
        }
    }

    private ArrayList<Coordinate> findRoute(ArrayList<Coordinate> result, CellType[][] dataMap, boolean[][] map, int row, int col, boolean done){
        //ArrayList<Direction> result = new ArrayList<>();
        if (dataMap[row][col].equals(CellType.EMPTY) || dataMap[row][col].equals(CellType.START) || dataMap[row][col].equals(CellType.END)){
            map[row][col] = true;
        }
        done = (row == map.length && col == map[0].length);
        if (!done && dataMap[row][col+1].equals(CellType.EMPTY)){
            result.add(new Coordinate(row, col+1));
            return findRoute(result, dataMap, map, row, col + 1, done);
        }
        if (!done && dataMap[row+1][col].equals(CellType.EMPTY)){
            result.add(new Coordinate(row+1, col));
            return findRoute(result, dataMap, map, row+1, col, done);
        }
        if (!done){
             map[row][col] = false;
             if (map[row][col-1]){
                 col--;
             }
             else {
                 row--;
             }
        }
        return result;
    }

    private boolean[][] mapLoader(CellType[][] map){
        boolean[][] result = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                result[i][j] = false;
            }
        }
        return result;

    }
}
