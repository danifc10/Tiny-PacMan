package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;

public class PacMan implements IGameEngineEvolve , IMazeElement {
    public static final char symbol= 'P';
    private int x; // posição horizontal
    private int y; // posição vertical
    private int lastX;
    private int lastY;
    private int direction; // direção atual
    private int speed; // velocidade
    private int life = 3;
    private MazeControl maze;

    public PacMan(){};

    public PacMan(int x, int y, int direction, int speed, MazeControl maze) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.maze = maze;
    }

    public void setMaze(MazeControl maze){
        this.maze = maze;
    }

    public boolean canMove(int x, int y){
        return !maze.checkIfWall(x, y) && maze.getXY(x, y).getSymbol() != 'Y';
    }

    public void movePacMan(){
        int x = getX();
        int y = getY();
        switch (getDirection()) {
            case 2 :
                if (y > 0 && canMove(x, y - 1)) {
                    checkIfWrap(x, y -1);
                    setDirection(2);
                    move();
                }
                break;
            case 1 :
                if (y < maze.getWidth() && canMove(x, y + 1)) {
                    checkIfWrap(x, y +1);
                    setDirection(1);
                    move();
                }
                break;
            case 3 :
                if (x > 0 && canMove(x - 1, y)) {
                    checkIfWrap(x - 1, y);
                    setDirection(3);
                    move();
                }
                break;
            case 4 :
                if (x < maze.getHeight() && canMove(x + 1, y)) {
                    checkIfWrap(x + 1, y);
                    setDirection(4);
                    move();
                }
                break;
        }

        maze.setXY(getX(), getY(), new PacMan());
        maze.remove(getLastX(), getLastY());
    }

    public void move() {
        lastX = getX();
        lastY = getY();

        switch (direction) {
            case 1 -> {
                this.y+=speed;
            }//RIGHT

            case 2 -> {
                this.y-=speed;
            }//LEFT

            case 3 -> {
                this.x-=speed;
            }// UP

            case 4 -> {
                this.x+=speed;
            }// DOWN

        }
    }

    public void checkIfWrap(int x, int y){
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
    public int getLife(){
        return life;
    }

    public void setLife(){this.life--;}

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
    public void evolve(IGameEngine gameEngine, long currentTime) {
        movePacMan();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
