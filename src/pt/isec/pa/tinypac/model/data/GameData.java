package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private MazeControl mazeControl;
    private PacMan pacMan;
    private Ghost [] ghosts;
    private List<Position> positions;
    private int totalPoints = 0;
    private int level = 1;
    private int countFruitPoints = 0;
    private int countPoints = 0;
    private int time = 0;
    private int pacManLife = 3;
    private int numOfGhosts = 4;
    private int lastGhostX , lastGhostY;
    private int speed = 0;
    private int thingsEaten = 0;
    private final int endTime = 300;

    public GameData() {
    }

    public void initGame() {
        totalPoints = 0;
        thingsEaten = 0;
        time = 0;
        mazeControl = new MazeControl(level);
        positions = mazeControl.getGhostStartPositions();
        pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), 1, mazeControl);
        ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), 2,  mazeControl, speed++),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), 2,  mazeControl, speed++),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), 1,  mazeControl, speed++),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), 2, mazeControl, speed++)
        };
    }

    // getter and setters to data
    public void setPoints(int i) {
        totalPoints = i;
    }
    public int getPoints() {
        return  totalPoints;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int i) {
        level = i;
    }
    public char[][] getMaze() {
        return mazeControl.getMazeControl();
    }
    public int getPacManLife() {
        return pacManLife;
    }
    public void setLife() {
        pacManLife--;
    }
    public void timer() {
        time++;
    }
    public int getTime(){
        return time;
    }

    // ghosts things
    public void setVulnerable() {
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
    }
    public void endVulnerable() {
        for (Ghost g : ghosts) {
            g.setVulnerable(false);
            g.roadMade = new ArrayList<>();
            g.road_index =0;
        }
    }
    public void setGhostsFree() {
        for(Ghost g: ghosts){
            g.move();
        }
    }
    public void moveGhosts() {
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead())
                ghost.move();
        }
    }
    public void vulnerableMoveGhosts(){
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead())
                ghost.vulnerableMove();
        }
    }

    // pacman things
    public void setPacManDirection(int direction) {
        if(direction == 1){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() + 1))
                pacMan.setDirection(1);
        }else if(direction == 2){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() - 1))
                pacMan.setDirection(2);
        }else if(direction== 3){
            if(pacMan.canMove(pacMan.getX() - 1, pacMan.getY()))
                pacMan.setDirection(3);
        }else if(direction== 4){
            if (pacMan.canMove(pacMan.getX() + 1, pacMan.getY()))
                pacMan.setDirection(4);
        }
    }
    public IMazeElement movePacMan() {
        int x = pacMan.getX();
        int y = pacMan.getY();

        IMazeElement eatElement = null;
        switch (pacMan.getDirection()) {
            case 2:
                if (y > 0 && canMove(x, y - 1)) {
                    eatElement = checks(x, y - 1);
                    pacMan.setDirection(2);
                    pacMan.move();
                }
                break;
            case 1:
                if (y < mazeControl.getWidth() && canMove(x, y + 1)) {
                    eatElement = checks(x, y + 1);
                    pacMan.setDirection(1);
                    pacMan.move();
                }
                break;
            case 3:
                if (x > 0 && canMove(x - 1, y)) {
                    eatElement = checks(x - 1, y);
                    pacMan.setDirection(3);
                    pacMan.move();
                }
                break;
            case 4:
                if (x < mazeControl.getHeight() && canMove(x + 1, y)) {
                    eatElement = checks(x + 1, y);
                    pacMan.setDirection(4);
                    pacMan.move();
                }
                break;
        }

        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacMan());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
        return eatElement;
    }
    public void eatGhost(int countGhostsPoints) {
        System.out.println("COMI FANTASMA ultimo x e y" + lastGhostX +" "+ lastGhostY);
        this.numOfGhosts--;
        totalPoints = 50 * countGhostsPoints;
        for(Ghost g : ghosts){
            if((g.getX() == lastGhostX && g.getY() == lastGhostY )||( g.getLastX() == lastGhostX && g.getLastY() == lastGhostY)) {
                g.setDead(true);
            }
        }
        for(Ghost g : ghosts){
            if(g.isDead())
                mazeControl.removeGhost(g.getX(), g.getY());
        }
    }
    public boolean canMove(int x, int y) {
        return !mazeControl.checkIfWall(x, y) && mazeControl.getXY(x, y).getSymbol() != 'Y';
    }
    public IMazeElement checks(int x, int y) {
        IMazeElement element = mazeControl.getXY(x, y);
        if (mazeControl.checkIfPoint(x, y)) {
            countPoints++;
            if (countPoints == 20) {
                mazeControl.setNewFruit();
                countPoints = 0;
            }
            totalPoints++;
            thingsEaten++;
        } else if (mazeControl.checkIfFruit(x, y)) {
            countFruitPoints++;
            thingsEaten++;
            totalPoints+= (25*countFruitPoints);
        } else if (mazeControl.checkIfPower(x, y)) {
            totalPoints+=10;
            thingsEaten++;
        } else if (mazeControl.checkIfWarp(x, y)) {
            pacMan.checkIfWarp(x, y);
        }else if (mazeControl.chekIfGhost(x, y)) {
            System.out.println("APANHADO");
            element = new Blinky();
            lastGhostX = x;
            lastGhostY = y;
        }
        return element;
    }


    // like a transition but not a really one
    public boolean checkIfWin(){
        System.out.println("COMIDOS: " + thingsEaten + " Total: " + mazeControl.getTotalPoints());
        if(thingsEaten >= mazeControl.getTotalPoints() || numOfGhosts == 0)
            return true;
        else
            return false;
    }
    public void levelUp() {
        level++;
        initGame();
    }
}
