package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;

public class PacMan implements IGameEngineEvolve {
    private int x; // posição horizontal
    private int y; // posição vertical
    private int lastX;
    private int lastY;
    private int direction; // direção atual
    private int speed; // velocidade
    private int life = 3;

    public PacMan(int x, int y, int direction, int speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void powerMode() {
        setSpeed(1);
    }

    public boolean checkLife() {
        return life > 0;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        move();
    }
}
