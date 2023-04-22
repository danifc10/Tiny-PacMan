package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public abstract class Ghost implements IGameEngineEvolve {
    protected int x, y; // posição do fantasma no labirinto
    protected int lastX, lastY;
    protected Direction direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected int vulnerabilityTimer; // tempo restante de vulnerabilidade do fantasma
    protected int targetX, targetY; // posição de destino do fantasma
    protected boolean isDead; // se o fantasma está morto ou não
    protected int speed;
    protected List<Direction> availableDirections;
    protected MazeControl maze;

    public Ghost(int x, int y, Direction direction, int speed, MazeControl maze){
        this.availableDirections = new ArrayList<>();
        this.availableDirections.add(Direction.DOWN);
        this.availableDirections.add(Direction.UP);
        this.availableDirections.add(Direction.RIGHT);
        this.availableDirections.add(Direction.LEFT);
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

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
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

    public abstract boolean getOut();

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

    public void setDirection(Direction direction) {
        this.direction = direction;
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
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
