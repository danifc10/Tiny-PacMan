package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.ui.gui.Game;

public class PacMan {

    private int x; // posição horizontal
    private int y; // posição vertical
    private int direction; // direção atual
    private int speed; // velocidade

    public PacMan(int x, int y, int direction, int speed) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public void move() {
        // atualiza a posição do Pac-Man de acordo com a direção atual
        switch (direction) {
            case Game.LEFT -> x -= speed;
            case Game.RIGHT -> x += speed;
            case Game.UP -> y -= speed;
            case Game.DOWN -> y += speed;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
