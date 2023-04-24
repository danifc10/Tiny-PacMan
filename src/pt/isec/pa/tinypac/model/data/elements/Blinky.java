package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blinky extends Ghost implements IMazeElement {
    private boolean isOut = false;
    Random random = new Random();
    private IMazeElement symbolRemove =null;
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

        if(!isOut) {
            getOut();
            return;
        }

        int nextX = x;
        int nextY = y;

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
        if ( maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY).getSymbol() == 'Y'){
            // escolhe uma nova direção válida
            List<Integer> possibleDirections = new ArrayList<>();
            int i = 0;
            for(int d = 0 ; d < 4; d++){
                if(d != direction && d != getOpositeDirection(direction)){
                    if(isDirectionValid(x, y, d)) {
                        possibleDirections.add(i, d);
                        i++;
                    }
                }
            }

            if(i == 0){
                direction = getOpositeDirection(direction);
            }else if(i == 1) {
                direction = possibleDirections.get(0);
            }else{
                int index;

                do{
                    index = random.nextInt(possibleDirections.size());
                }while( index == getOpositeDirection(direction));
                direction = possibleDirections.get(index);
            }
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

        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,new Blinky());

    }

    private boolean checkIfGate(int nextX, int nextY) {
        return maze.getXY(nextX, nextY).getSymbol() == 'Y';
    }

    @Override
    public boolean getOut() {
        while(!isOut){
            int nextX = x;
            int nextY = y;

            if(this.maze.getXY(x, y + 1).getSymbol() == 'Y'){
                direction = 2;
            }else if(this.maze.getXY(x, y -1 ).getSymbol() == 'Y'){
                direction = 3;
            }else if(this.maze.getXY(x - 1, y).getSymbol() == 'Y'){
                direction = 0;
            }else if(this.maze.getXY(x + 1, y).getSymbol() == 'Y'){
                direction = 1;
            }

            if (direction == 0) {
                nextX--;
            } else if (direction == 1) {
                nextX++;
            } else if (direction == 2) {
                nextY++;
            } else if (direction == 3) {
                nextY--;
            }
            int newdirection;
            if(!maze.checkIfWall(nextX, nextY)){
                lastX = x;
                lastY = y;
                x = nextX;
                y = nextY;
            }else{
                do{
                    newdirection = random.nextInt(0,4);

                    if (direction == 0) {
                        nextX--;
                    } else if (direction == 1) {
                        nextX++;
                    } else if (direction == 2) {
                        nextY++;
                    } else if (direction == 3) {
                        nextY--;
                    }
                }while(direction == newdirection || maze.checkIfWall(nextX, nextY));
                lastX = x;
                lastY = y;
                x = nextX;
                y = nextY;

            }

            maze.remove(lastX, lastY);
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
                maze.setXY(lastX, lastY, symbolRemove);
            }

            symbolRemove = maze.getXY(x, y);
            this.maze.setXY(x,y,new Blinky());
            if(x < maze.getXghostStart()) {
                isOut = true;
            }
        }
        direction = 3;
        return false;
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
        if (x > 0 && maze.checkIfWallGhost(x - 1, y)) {
            numWalls++;
        }
        if (x < maze.getHeight() - 1 &&  maze.checkIfWallGhost(x + 1, y)) {
            numWalls++;
        }
        if (y > 0 &&  maze.checkIfWallGhost(x , y - 1)) {
            numWalls++;
        }
        if (y < maze.getWidth() - 1 &&  maze.checkIfWallGhost(x , y + 1)) {
            numWalls++;
        }

        // é um cruzamento se tiver mais de 2 paredes nas células vizinhas
        return numWalls >= 3;
    }

    private boolean isDirectionValid( int x, int y, int direction) {

        int nextX = x;
        int nextY = y;

        if (direction == 0) {
            --nextX;
        } else if (direction == 1) {
            ++nextX;
        } else if (direction == 2) {
            ++nextY;
        } else if (direction == 3) {
            --nextY;
        }

        // verifica se a próxima célula é uma parede
        if (maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY).getSymbol() == 'Y')
            return false;
        else
            return true;


    }

}



