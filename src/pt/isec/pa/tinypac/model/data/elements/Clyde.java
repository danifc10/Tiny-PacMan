package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.GhostGate;
import pt.isec.pa.tinypac.model.data.Point;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class Clyde extends Ghost implements IMazeElement, Serializable {
    private static final char symbol = 'C';

    public Clyde(int x, int y, Direction direction, MazeControl maze, int speed) {
        super(x, y, direction, maze, speed);
        this.roadMade = new ArrayList<>();
        this.isOut = false;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void move() {
        if(!isOut){
            getOut();
            return;
        }

        int nextX = x;
        int nextY = y;

        // verifica se o pacman esta no seu campo de visao
        if(checkIfPacman(x, y) && !vulnerable){
            followPacMan();
            return;
        }

        switch (direction){
            case UP -> nextX--;
            case DOWN -> nextX++;
            case LEFT -> nextY--;
            case RIGHT -> nextY++;
        }

        if( maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY) instanceof GhostGate){ // verifica se a próxima célula é uma parede ou cruzamento
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

        if(road_index < 0 ) {
            road_index = 0;
        }
        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,this);
        road_index++;
    }

    private boolean checkIfPacman(int x, int y) {
        int count = 0;
        Position pacManPosition = maze.getPacManPosition();
        if(pacManPosition == null) {
            return false;
        }
        else if(pacManPosition.getX() == x){
            if(this.y < pacManPosition.getY()) {
                for (int j = this.y; j < pacManPosition.getY(); j++)
                    if (maze.checkIfWallGhost(x, j))
                        count++;
            }else if(this.y > pacManPosition.getY()){
                for (int j = pacManPosition.getY(); j < this.y; j++)
                    if (maze.checkIfWallGhost(x, j))
                        count++;
            }

            if(count != 0)
                return false;
            else
                return true;
        }else if(pacManPosition.getY() == y) {
            if(this.x < pacManPosition.getX()) {
                for (int j = this.x; j < pacManPosition.getX(); j++)
                    if (maze.checkIfWallGhost(j, y))
                        count++;
            }else if(this.x > pacManPosition.getX()){
                for (int j = pacManPosition.getX(); j < this.x; j++)
                    if (maze.checkIfWallGhost(j, y))
                        count++;
            }

            return count == 0;
        }

        return false;
    }

    private void followPacMan(){
        lastX = x;
        lastY = y;
        int pacManX = maze.getPacManPosition().getX(), pacManY = maze.getPacManPosition().getY();
        if( pacManX == x){
            if( this.y <pacManY){
                if(isDirectionValid(x, y, Direction.RIGHT))
                    this.y++;
            }else {
                if (isDirectionValid(x, y, Direction.LEFT))
                    this.y--;
            }
        }else if(pacManY == y){
            if( this.x < pacManX){
                if(isDirectionValid(x, y, Direction.DOWN))
                    this.x++;
            }else {
                if (isDirectionValid(x, y, Direction.UP))
                    this.x--;
            }
        }

        if(road_index < 0) {
            road_index = 0;
        }

        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);

        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }else{
            maze.setXY(lastX, lastY,new Point());
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,this);
        road_index++;
    }


    private boolean isDirectionValid( int x, int y, Direction direction) {
        int nextX = x;
        int nextY = y;

        switch (direction){
            case UP -> nextX--;
            case DOWN -> nextX++;
            case LEFT -> nextY--;
            case RIGHT -> nextY++;
        }

        // verifica se a próxima célula é uma parede
        if(maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY) instanceof GhostGate)
            return false;
        else
            return true;
    }

}
