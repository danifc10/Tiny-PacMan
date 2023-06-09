package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;

import java.io.Serializable;

public class PacMan implements IMazeElement , Serializable {
    public static final char symbol= 'P';
    private int x; // row
    private int y; // col
    private int lastX;
    private int lastY;
    private int direction; // direção atual
    private MazeControl maze;

    public PacMan(){};

    public PacMan(int x, int y, int direction, MazeControl maze) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.maze = maze;
    }

    public void setMaze(MazeControl maze){
        this.maze = maze;
    }

    public boolean canMove(int x, int y){
        return !maze.checkIfWall(x, y) && maze.getXY(x, y).getSymbol() != 'Y';
    }

    public void move() {
        lastX = getX();
        lastY = getY();

        switch (direction) {
            case 1 -> {
                this.y++;
            }//RIGHT

            case 2 -> {
                this.y--;
            }//LEFT

            case 3 -> {
                this.x--;
            }// UP

            case 4 -> {
                this.x++;
            }// DOWN

        }
    }

    public void checkIfWarp(int x, int y){
        maze.remove(this.x, this.y);
        for(int i = 0; i < maze.getHeight() ; i++){
            for(int j = 0; j < maze.getWidth() ; j++){
                if((j != y && i == x ) && (maze.getXY(i,j).getSymbol() == 'W')){
                    setY(j);
                    setX(i);
                }
            }
        }
    }

    // getters e setters
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
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
