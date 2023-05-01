package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.List;

public abstract class Ghost {
    protected int x, y; // posição do fantasma no labirinto
    protected int lastX, lastY;
    protected int direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected int vulnerabilityTimer; // tempo restante de vulnerabilidade do fantasma
    protected int targetX, targetY; // posição de destino do fantasma
    protected boolean isDead; // se o fantasma está morto ou não
    protected int speed;
    protected MazeControl maze;
    protected boolean isOut;
    private int time = 0;
    public List<Position> roadMade;
    public int road_index = 0;

    public Ghost(){};

    public Ghost(int x, int y, int direction, int speed, MazeControl maze){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.vulnerable = false;
        this.vulnerabilityTimer = 0;
        this.targetX = x;
        this.targetY = y;
        this.isDead = false;
        this.speed = speed;
        this.maze = maze;
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

    public boolean isVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setIsOut(boolean b) {
        isOut = b;
    }
}
