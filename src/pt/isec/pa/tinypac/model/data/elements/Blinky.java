package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blinky extends Ghost implements IMazeElement {
    Random random = new Random();
    public Blinky(int x, int y, int direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
    }

    public Blinky(){};
    public static final char symbol = 'B';

    @Override
    public char getSymbol() {

        return symbol;
    }

    @Override
    public void move() {
        int nextX = x;
        int nextY = y;

        // verifica a próxima célula na direção atual
        if (direction == 0) {
            nextX--;
        } else if (direction == 1) {
            nextX++;
        } else if (direction == 2) {
            nextY++;
        } else if (direction == 3) {
            nextY--;
        }

        // verifica se a próxima célula é uma parede ou cruzamento
        if ( maze.checkIfWall(nextX, nextY)) {
            // escolhe uma nova direção válida
            List<Integer> possibleDirections = new ArrayList<>();
            int i = 0;
            for(int d = 0 ; d < 4; d++){
                if(d != getOpositeDirection(direction) && d != direction){
                    possibleDirections.add(i, d);
                    i++;
                }
            }
            int index = random.nextInt(i);
            direction = possibleDirections.get(index);
        }

        lastX = x;
        lastY = y;
        // move o fantasma na direção atual
        if (direction == 0) {
            x--;
        } else if (direction == 1) {
            x++;
        } else if (direction == 2) {
            y++;
        } else if (direction == 3) {
            y--;
        }

        this.maze.remove(lastX, lastY);
        this.maze.setXY(x,y,new Blinky());
    }

    @Override
    public boolean getOut() {
        move();
        return true;
    }

    private int getOpositeDirection(int direction) {
        switch (direction){
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
        }
        return 0;
    }
/*
    private void checkNeighbords(int x, int y) {
        if(this.maze.getXY(x, y + 1).getSymbol() == 'P'){ //
            setDirection(Direction.RIGHT);
        }else if(this.maze.getXY(x, y -1 ).getSymbol() == 'P'){
            setDirection(Direction.LEFT);
        }else if(this.maze.getXY(x - 1, y).getSymbol() == 'P'){
            setDirection(Direction.UP);
        }else if(this.maze.getXY(x + 1, y).getSymbol() == 'P'){
            setDirection(Direction.DOWN);
        }
    }*/

    private boolean isCrossroad(int x, int y) {
        int numWalls = 0;

        // conta o número de paredes nas células vizinhas
        if (x > 0 && maze.checkIfWall(x - 1, y)) {
            numWalls++;
        }
        if (x < maze.getHeight() - 1 &&  maze.checkIfWall(x + 1, y)) {
            numWalls++;
        }
        if (y > 0 &&  maze.checkIfWall(x , y - 1)) {
            numWalls++;
        }
        if (y < maze.getWidth() - 1 &&  maze.checkIfWall(x , y + 1)) {
            numWalls++;
        }

        // é um cruzamento se tiver mais de 2 paredes nas células vizinhas
        return numWalls >= 3;
    }

    private boolean isDirectionValid( int x, int y, int direction) {
        int nextX = x;
        int nextY = y;

        // verifica a próxima célula na nova direção
        if (direction == 0) { // UP
            nextX--;
        } else if (direction == 1) { // DOWN
            nextX++;
        } else if (direction == 2) { // RIGHT
            nextY++;
        } else if (direction == 3) { // LEFT
            nextY--;
        }

        // verifica se a próxima célula é uma parede ou cruzamento
        if (maze.checkIfWall(nextX, nextY) || isCrossroad(nextX,nextY)) {
            return false;
        }
        return true;
    }

}



