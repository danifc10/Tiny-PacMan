package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.List;

public abstract class Ghost implements IMazeElement {
    protected int x, y; // posição do fantasma no labirinto
    protected int lastX, lastY;
    protected int direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected boolean isDead; // se o fantasma está morto ou não
    protected MazeControl maze;
    protected boolean isOut;  // se esta na jaula ou nao
    public List<Position> roadMade;
    public int road_index = 0;
    protected int speed;

    public Ghost(){};

    public Ghost(int x, int y, int direction, MazeControl maze, int speed){
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

    public abstract void vulnerableMove();

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

}
