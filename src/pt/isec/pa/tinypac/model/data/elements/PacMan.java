package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.Serializable;
/**
 * class representing PacMan
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class PacMan implements IMazeElement , Serializable {
    /**
     * Reference to PacMan symbol
     */
    public static final char symbol= 'P';
    private int x; // row
    private int y; // col
    private int lastX;
    private int lastY;
    private Direction direction; // direção atual
    private MazeControl maze;


    /**
     * Default constructor
     * @param (x,y,direction, maze) all PacMan properties
     *
     */
    public PacMan(int x, int y, Direction direction, MazeControl maze) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.maze = maze;
    }
    /**
     * function to see if PacMan can move for that position
     * @param (x,y) row and collumn
     * @return true/false
     */
    public boolean canMove(int x, int y){
        return !(maze.getXY(x, y) instanceof Wall) && !(maze.getXY(x, y) instanceof GhostGate);
    }

    /**
     * PacMan move
     *
     */
    public void move() {
        lastX = getX();
        lastY = getY();

        switch (direction) {
            case RIGHT -> this.y++;
            case LEFT -> this.y--;
            case UP -> this.x--;
            case DOWN -> this.x++;
        }

        maze.setXY(x, y, this);
        maze.remove(lastX, lastY);
    }

    /**
     * PacMan enter in a warp function
     * @param (x,y) position to check
     */
    public void checkIfWarp(int x, int y){
        maze.remove(this.x, this.y);
        for(int i = 0; i < maze.getHeight() ; i++){
            for(int j = 0; j < maze.getWidth() ; j++){
                if((j != y && i == x ) && (maze.getXY(i,j) instanceof WarpZone)){
                    setY(j);
                    setX(i);
                }
            }
        }
    }

    /**
     * Get row
     * @return x row
     */
    public int getX() {
        return x;
    }
    /**
     * Set new row
     * @param  x new row
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Get collumn
     * @return y collumn
     */
    public int getY() {
        return y;
    }
    /**
     * Set new collumn
     * @param  y new collumn
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Get current direction
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }
    /**
     * Set new direction
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    /**
     * Get last row
     * @return lastX last row
     */
    public int getLastX() {
        return lastX;
    }
    /**
     * Get last collumn
     * @return lastY last collumn
     */
    public int getLastY() {
        return lastY;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
