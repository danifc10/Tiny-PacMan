package pt.isec.pa.tinypac.model.fsm.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class PacMan implements IMazeElement {
    private char symbol = 'P';
    private int x; // posição horizontal
    private int y; // posição vertical
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
        switch (direction){
            case 1: //RIGHT
                this.x++;
                break;
            case 2: //LEFT
                this.x--;
                break;
            case 3: // UP
                this.y++;
                break;
            case 4: // DOWN
                this.y--;
                break;
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

    public void powerMode() {
    }

    public boolean checkLife() {
        return life > 0;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
