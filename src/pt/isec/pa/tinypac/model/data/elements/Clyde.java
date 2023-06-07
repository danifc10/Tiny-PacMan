package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clyde extends Ghost{
    Random random = new Random();
    private IMazeElement symbolRemove =null;
    private static final char symbol = 'C';

    public Clyde(int x, int y, int direction, MazeControl maze, int speed) {
        super(x, y, direction, maze, speed);
        this.roadMade = new ArrayList<>();
        this.isOut = false;
    }

    public Clyde(){};

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

        // verifica se o pacman esta no seu campo de visao
        if(checkIfPacman(x, y) && !vulnerable){
            followPacMan();
            return;
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

        if( maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY).getSymbol() == 'Y'){ // verifica se a próxima célula é uma parede ou cruzamento
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

        if (direction == 0) {
            x--;
        } else if (direction == 1) {
            x++;
        } else if (direction == 2) {
            y++;
        } else if (direction == 3) {
            y--;
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
        this.maze.setXY(x,y,new Clyde());
        road_index++;
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
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
                maze.setXY(lastX, lastY, symbolRemove);
            }
            symbolRemove = maze.getXY(x, y);
            this.maze.setXY(x, y, new Clyde());
        }
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

            if(count != 0)
                return false;
            else
                return true;
        }

        return false;
    }

    public void followPacMan(){
        lastX = x;
        lastY = y;
        int pacManX = maze.getPacManPosition().getX(), pacManY = maze.getPacManPosition().getY();
        if( pacManX == x){
            if( this.y <pacManY){
                if(isDirectionValid(x, y, 2))
                    this.y++;
            }else {
                if (isDirectionValid(x, y, 3))
                    this.y--;
            }
        }else if(pacManY == y){
            if( this.x < pacManX){
                if(isDirectionValid(x, y, 1))
                    this.x++;
            }else {
                if (isDirectionValid(x, y, 0))
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
        }

        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,new Clyde());
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

            if(road_index < 0) {
                road_index = 0;
            }

            roadMade.add(road_index, new Position(x, y));
            maze.remove(lastX, lastY);
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
                maze.setXY(lastX, lastY, symbolRemove);
            }

            symbolRemove = maze.getXY(x, y);
            this.maze.setXY(x,y,new Clyde());
            if(x < maze.getGhostGate().getX()) {
                isOut = true;
            }
            road_index++;
        }
        direction = 2;

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
        if(maze.checkIfWallGhost(nextX, nextY) || maze.getXY(nextX, nextY).getSymbol() == 'Y')
            return false;
        else
            return true;
    }

}
