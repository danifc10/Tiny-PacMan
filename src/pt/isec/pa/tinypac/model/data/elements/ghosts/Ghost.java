package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * class representing the Ghosts
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public abstract class Ghost implements IMazeElement , Serializable {
    /**
     * Reference to current position
     */
    protected int x, y; // posição do fantasma no labirinto
    /**
     * Reference to the last position
     */
    protected int lastX, lastY;
    /**
     * Reference to the current direction
     */
    protected Direction direction; // direção atual do fantasma
    /**
     * Boolean if is vulnerable or not
     */
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    /**
     * Boolean if is dead or not
     */
    protected boolean isDead; // se o fantasma está morto ou não
    protected MazeControl maze;
    /**
     * Boolean if is out of the jail or not
     */
    protected boolean isOut;  // se esta na jaula ou nao
    /**
     * Array list for the ghost path
     */
    public List<Position> roadMade; // caminho percorrido pelo fantasma
    protected IMazeElement symbolRemove = null;
    /**
     * Reference to index for the array roadMade
     */
    public int road_index = 0;
    /**
     * Reference to the speed
     */
    protected int speed;

    /**
     * Default ghost constructor
     * @param (x, y, direction, maze, speed) all the ghost properties
     *
     */
    public Ghost(int x, int y, Direction direction, MazeControl maze, int speed){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.vulnerable = false;
        this.isDead = false;
        this.maze = maze;
        this.speed = speed;
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
    /**
     * Ghost move
     */
    public abstract void move();
    /**
     * Get current row
     * @return x current row
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
     * Get current collumn
     * @return y current collumn
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
    public int getDirection() {
        return 0;
    }
    /**
     * When gosts are vulnerable or not
     * @param vulnerable true if will be vulnerable false if not
     */
    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }
    /**
     * Confirm is a ghost is dead
     * @return isDead true if is dead and false if not
     */
    public boolean isDead() {
        return isDead;
    }
    /**
     * "kill" a ghost
     * @param  dead if true ghost die
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Functio to get out of jail
     *
     */
    public void getOut() {
        Position portal = new Position(maze.getGhostGate().getX(), maze.getGhostGate().getY());

        direction = getValidDirection(portal, direction);
        this.x = getNextPosition(portal, direction).getX();
        this.y = getNextPosition(portal, direction).getY();

        lastX = x;
        lastY = y;

        symbolRemove = maze.getXY(x, y);
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
            maze.setXY(lastX, lastY, symbolRemove);
        }
        this.maze.setXY(x, y, this);

        isOut = true;
    }

    /**
     * Vulnerable ghost move
     *
     */
    public void vulnerableMove(){
        --road_index;
        if(road_index >= 0) {
            this.lastX = x;
            this.lastY = y;

            this.x = roadMade.get(road_index).getX();
            this.y = roadMade.get(road_index).getY();
            maze.remove(lastX, lastY);
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
                maze.setXY(lastX, lastY, symbolRemove);
            }
            symbolRemove = maze.getXY(x, y);
            this.maze.setXY(x, y, this);
        }

    }

    /**
     * Get a valid direction to the ghost gate
     * @return direction
     */
    public Direction getValidDirection(Position ghostGate, Direction direction) {
        List<Direction> validDirections = new ArrayList<>();

        for(Direction dir: Direction.values()){
            Position nextPosition = getNextPosition(ghostGate, dir);
            if(maze.checkIfWalk(nextPosition.getX(), nextPosition.getY())){
                validDirections.add(dir);
            }
        }

        if( (direction != null) && (validDirections.size()  > 1 )&& (validDirections.contains(Direction.opositeDirection(direction)))){
            validDirections.remove(Direction.opositeDirection(direction));
        }

        if(!(validDirections.isEmpty())){
            Random rnd = new Random();

            Direction validDir = validDirections.get(rnd.nextInt(validDirections.size()));
            return validDir;
        }
        return null;
    }

    /**
     * Get a valid nextPosition
     * @return nextPosition a new Position
     */
    public Position getNextPosition(Position currentPosition, Direction direction){
        int nextX = currentPosition.getX();
        int nextY = currentPosition.getY();

        switch (direction){
            case UP -> nextX--;
            case DOWN -> nextX++;
            case LEFT -> nextY--;
            case RIGHT -> nextY++;
        }

        return new Position(nextX, nextY);
    }


}
