package pt.isec.pa.tinypac.model.fsm;

public abstract class Ghost {
    protected int x, y; // posição do fantasma no labirinto
    protected int direction; // direção atual do fantasma
    protected boolean vulnerable; // se o fantasma está vulnerável ou não
    protected int vulnerabilityTimer; // tempo restante de vulnerabilidade do fantasma
    protected int targetX, targetY; // posição de destino do fantasma
    protected boolean isDead; // se o fantasma está morto ou não


    public Ghost(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.vulnerable = false;
        this.vulnerabilityTimer = 0;
        this.targetX = x;
        this.targetY = y;
        this.isDead = false;
    }
    public abstract void move();
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
}
