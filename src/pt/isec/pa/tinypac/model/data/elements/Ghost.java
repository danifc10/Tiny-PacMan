package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost implements IMazeElement , Serializable {
    protected int x, y; // posição do fantasma no labirinto
    protected int lastX, lastY;
    protected Direction direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected boolean isDead; // se o fantasma está morto ou não
    protected MazeControl maze;
    protected boolean isOut;  // se esta na jaula ou nao
    public List<Position> roadMade; // caminho percorrido pelo fantasma
    protected IMazeElement symbolRemove = null;
    public int road_index = 0;
    protected int speed;

    public Ghost(int x, int y, Direction direction, MazeControl maze, int speed){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.vulnerable = false;
        this.isDead = false;
        this.maze = maze;
        this.speed = speed;
    }

    public void setMaze(MazeControl maze){
        this.maze = maze;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public abstract void move();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return 0;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

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

    public Direction getValidDirection(Position portal, Direction direction) {
        List<Direction> validDirections = new ArrayList<>();

        for(Direction dir: Direction.values()){
            Position nextPosition = getNextPosition(portal, dir);
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
