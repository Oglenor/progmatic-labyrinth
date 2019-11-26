package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private int width;
    private int height;
    public CellType[][] map ;//= new CellType[5][5];
    private Coordinate playerCoordinates;// = new Coordinate(0,1);


    public LabyrinthImpl() {

        //player = new RandomPlayer();
        //loadLabyrinthFile(fileName);
    }

    @Override
    public int getWidth() {
        if (this.width == 0){
            return -1;
        }
        else {
            return this.width;
        }
    }

    @Override
    public int getHeight() {
        if (this.height == 0){
            return -1;
        }
        else {
            return this.height;
        }
    }

    @Override
    public void loadLabyrinthFile(String fileName) {

        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());

            setSize(width, height);

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            //map[hh][ww] = CellType.WALL;

                            try{
                                setCellType(new Coordinate(ww,hh), CellType.WALL);
                            }
                            catch (CellException ex){
                                System.out.println(ex.getMessage());
                            }

                            break;

                        case 'E':
                            try{
                                setCellType(new Coordinate(ww,hh), CellType.END);
                            }
                            catch (CellException ex){
                                System.out.println(ex.getMessage());
                            }
                            //map[hh][ww] = CellType.END;
                            break;

                        case 'S':
                            try{
                                playerCoordinates = new Coordinate( ww,hh);
                                //map[hh][ww] = CellType.START;
                                setCellType(new Coordinate(ww,hh), CellType.START);
                            }
                            catch (CellException ex){
                                System.out.println(ex.getMessage());
                            }
                            //map[hh][ww] = CellType.START;
                            break;
                        /*
                        case ' ':
                            //map[hh][ww] = CellType.EMPTY;
                            try{
                                setCellType(new Coordinate(ww,hh), CellType.EMPTY);
                            }
                            catch (CellException ex){
                                System.out.println(ex.getMessage());
                            }
                            break;

                         */
                    }
                    System.out.print(map[hh][ww]);
                }
                System.out.println();
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if ((c.getRow() < this.height && c.getRow() > -1) && (c.getCol() < this.width && c.getCol() > -1)) {
            return map[c.getRow()][c.getCol()];
        }
        else{
            throw new CellException(c, "Nem létező koordináta kivétel keletkezett.");
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new CellType[height][width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                map[i][j] = CellType.EMPTY;
            }
        }

    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {

        if (type.equals(CellType.START)){

            playerCoordinates = c;
        }
        if ((c.getRow() < this.height && c.getRow() > -1) && (c.getCol() < this.width && c.getCol() > -1)) {

            this.map[c.getRow()][c.getCol()] = type;
        }
        else{
            throw new CellException(c, "Nem létező koordináta kivétel keletkezett.");
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return this.playerCoordinates;
    }

    @Override
    public boolean hasPlayerFinished() {

            if ((map[playerCoordinates.getRow()][playerCoordinates.getCol()]).equals(CellType.END)){
                return true;
            }
            else{
                return false;
            }



    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> directions = new ArrayList<Direction>();

        //for (int i = 0; i < 4; i++) {
            if (playerCoordinates.getRow() > 0 && !(map[playerCoordinates.getRow()-1][playerCoordinates.getCol()].equals(CellType.WALL)) ){
                directions.add(Direction.NORTH);
            }
            if (playerCoordinates.getCol() < width-1 && !(map[playerCoordinates.getRow()][playerCoordinates.getCol()+1].equals(CellType.WALL))){
                directions.add(Direction.EAST);
            }

            if (playerCoordinates.getRow() < height-1 && !(map[playerCoordinates.getRow()+1][playerCoordinates.getCol()].equals(CellType.WALL))){
                directions.add(Direction.SOUTH);
            }
            if (playerCoordinates.getCol() > 0 && !(map[playerCoordinates.getRow()][playerCoordinates.getCol()-1].equals(CellType.WALL))){
                directions.add(Direction.WEST);
            }

        //}
        return directions;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        List<Direction> possibleDirection = possibleMoves();
        if (possibleDirection.contains(direction)){
            if (direction.equals(Direction.NORTH)){
                playerCoordinates = new Coordinate(playerCoordinates.getCol(),playerCoordinates.getRow()-1);
            }
            if (direction.equals(Direction.EAST)){
                playerCoordinates = new Coordinate(playerCoordinates.getCol()+1,playerCoordinates.getRow());
            }
            if (direction.equals(Direction.SOUTH)){
                playerCoordinates = new Coordinate(playerCoordinates.getCol(),playerCoordinates.getRow()+1);
            }
            if (direction.equals(Direction.WEST)){
                playerCoordinates = new Coordinate(playerCoordinates.getCol()-1,playerCoordinates.getRow());
            }
        }
        else{
            throw new InvalidMoveException();
        }
    }
}
