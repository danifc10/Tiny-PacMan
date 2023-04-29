package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.List;

public abstract class Ghost implements IGameEngineEvolve {
    protected int x, y; // posição do fantasma no labirinto
    protected int lastX, lastY;
    protected int direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected int vulnerabilityTimer; // tempo restante de vulnerabilidade do fantasma
    protected int targetX, targetY; // posição de destino do fantasma
    protected boolean isDead; // se o fantasma está morto ou não
    protected int speed;
    protected MazeControl maze;
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

    public boolean canMove(int x, int y, int direction){
        switch (direction){
            case 1: // DOWN
                x++;
            case 2: // RIGHT
                y++;
            case 0: // UP
                x--;
            case 3: // LEFT
                y--;
        }

        if (x < 0 || y < 0 || x >= maze.getWidth() || y >= maze.getHeight()) {
            return false;
        }
        System.out.println(maze.checkIfWall(x, y));
        return !maze.checkIfWall(x, y);
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        time++;
        if (time >= 30) // aproximadamente 5 segundos
            move();
    }

    public void setMaze(MazeControl maze){
        this.maze = maze;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
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

    public int getVulnerabilityTimer() {
        return vulnerabilityTimer;
    }

    public void setVulnerabilityTimer(int vulnerabilityTimer) {
        this.vulnerabilityTimer = vulnerabilityTimer;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        maze.removeGhost(this.x, this.y);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
