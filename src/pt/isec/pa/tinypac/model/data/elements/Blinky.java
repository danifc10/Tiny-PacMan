package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blinky extends Ghost implements IMazeElement {
    private static final char symbol = 'B';
    private boolean isOut = false;
    Random random = new Random();
    private IMazeElement symbolRemove =null;

    public Blinky(int x, int y, int direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
        this.roadMade = new ArrayList<>();
    }

    public Blinky(){};

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
        if(road_index == -1) {
            road_index = 0;
        }
        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,new Blinky());
        road_index++;
    }

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
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
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

    @Override
    public void vulnerableMove() {
        --road_index;
        if(road_index >= 0) {
            this.lastX = x;
            this.lastY = y;
            this.x = roadMade.get(road_index).getX();
            this.y = roadMade.get(road_index).getY();
            maze.remove(lastX, lastY);
            this.maze.setXY(x, y, new Blinky());
        }
    }

}



