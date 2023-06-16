package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * class representing the Blinky Ghost
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Blinky extends Ghost implements IMazeElement, Serializable {
    private static final char symbol = 'B';

    /**
     * Default ghost constructor
     * @param (x, y, direction, maze, speed) all the ghost properties
     *
     */
    public Blinky(int x, int y, Direction direction, MazeControl maze, int speed) {
        super(x, y, direction, maze, speed);
        this.roadMade = new ArrayList<>();
        this.isOut = false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    /**
     * Movement of Blinky
     *
     */
    @Override
    public void move() {

        if(!isOut){
            getOut();
            return;
        }
        int nextX = x;
        int nextY = y;

        switch (direction){
            case UP -> nextX--;
            case DOWN -> nextX++;
            case LEFT -> nextY--;
            case RIGHT -> nextY++;
        }

        // verifica se a próxima célula é uma parede ou cruzamento
        if ( maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY).getSymbol() == 'Y'){
            // escolhe uma nova direção válida
            Position pos = new Position(x, y);
            direction = getValidDirection(pos, direction);
        }

        lastX = x;
        lastY = y;

        // move o fantasma na direção atual
        switch (direction){
            case UP -> x--;
            case DOWN -> x++;
            case LEFT -> y--;
            case RIGHT -> y++;
        }

        if(road_index < 0) {
            road_index = 0;
        }

        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'C' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,this);
        road_index++;
    }

}


