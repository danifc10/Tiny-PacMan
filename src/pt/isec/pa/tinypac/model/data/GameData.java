package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameData implements Serializable {
    private MazeControl mazeControl;
    private PacMan pacMan;
    private Ghost [] ghosts;
    private int totalPoints = 0;
    private int level = 1;
    private int countFruitPoints = 0;
    private int countPoints = 0;
    private int time = 0;
    private int pacManLife = 3;
    private int numOfGhosts = 4;
    private int speed = 0;
    private boolean stopTimer;
    private int countGhostsPoints = 0;
    private int pacManFood = 0;

    public GameData() {
        //initGame();
    }

    public void initGame() {
        pacManFood = 0;
        countPoints = 0;
        numOfGhosts = 4;
        countGhostsPoints = 0;
        time = 0;
        mazeControl = new MazeControl(level);
        List<Position> positions = mazeControl.getGhostStartPositions();
        pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), Direction.RIGHT, mazeControl);
        ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), Direction.UP,  mazeControl, speed++),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), Direction.RIGHT,  mazeControl, speed++),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), Direction.LEFT,  mazeControl, speed++),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), Direction.DOWN, mazeControl, speed++)
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
    public void timer() {
        if(!stopTimer)
            time++;
    }
    public int getTime(){
        return time;
    }
    public int getNumGhosts() {
        return numOfGhosts;
    }

    // ghosts things
    public void setGhostsPoints(){
        countGhostsPoints++;
        this.numOfGhosts--;
        totalPoints = totalPoints + (50 * countGhostsPoints);
    }
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
    public boolean moveGhosts() {
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead()) {
                ghost.move();
                if (ghost.getX() == pacMan.getX() && ghost.getY() == pacMan.getY()){
                    return true;
                }
            }
        }
        return false;
    }
    public void vulnerableMoveGhosts(){
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead()) {
                ghost.vulnerableMove();
                if (ghost.getX() == pacMan.getX() && ghost.getY() == pacMan.getY()){
                    ghost.setDead(true);
                    mazeControl.removeGhost(ghost.getX(), ghost.getY());
                    setGhostsPoints();
                }
            }
        }
    }

    // pacman things
    public void setPacManDirection(Direction direction) {
        stopTimer = false;
        if(direction == Direction.RIGHT){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() + 1))
                pacMan.setDirection(Direction.RIGHT);
        }else if(direction == Direction.LEFT){
           if(pacMan.canMove(pacMan.getX(), pacMan.getY() - 1))
                pacMan.setDirection(Direction.LEFT);
        }else if(direction == Direction.UP){
            if(pacMan.canMove(pacMan.getX() - 1, pacMan.getY()))
                pacMan.setDirection(Direction.UP);
        }else if(direction == Direction.DOWN){
            if (pacMan.canMove(pacMan.getX() + 1, pacMan.getY()))
                pacMan.setDirection(Direction.DOWN);
        }
    }

    public IMazeElement movePacMan() {
        int x = pacMan.getX();
        int y = pacMan.getY();

        IMazeElement eatElement = null;
        switch (pacMan.getDirection()) {
            case LEFT -> {
                if (y > 0 && canMove(x, y - 1)) {
                    eatElement = checks(x, y - 1);
                    pacMan.setDirection(Direction.LEFT);
                    pacMan.move();
                }
            }
            case RIGHT -> {
                if (y < mazeControl.getWidth() && canMove(x, y + 1)) {
                    eatElement = checks(x, y + 1);
                    pacMan.setDirection(Direction.RIGHT);
                    pacMan.move();
                }
            }
            case UP -> {
                if (x > 0 && canMove(x - 1, y)) {
                    eatElement = checks(x - 1, y);
                    pacMan.setDirection(Direction.UP);
                    pacMan.move();
                }
            }
            case DOWN -> {
                if (x < mazeControl.getHeight() && canMove(x + 1, y)) {
                    eatElement = checks(x + 1, y);
                    pacMan.setDirection(Direction.DOWN);
                    pacMan.move();
                }
            }
        }

        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacMan());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
        return eatElement;
    }

    public void eatGhost(IMazeElement element) {
        for(Ghost g : ghosts){
            if(g.getSymbol() == element.getSymbol() || (g.getX() == pacMan.getX() && g.getY() == pacMan.getY())){
                g.setDead(true);
                mazeControl.remove(g.getLastX(), g.getLastY());
                setGhostsPoints();
            }
        }
    }

    public boolean canMove(int x, int y) {
        return (!mazeControl.checkIfWall(x, y) && !(mazeControl.getXY(x, y) instanceof GhostGate));
    }

    public IMazeElement checks(int x, int y) {
        IMazeElement element = mazeControl.getXY(x, y);
        if (element instanceof Point) {
            countPoints++;
            if (countPoints == 20) {
                mazeControl.setNewFruit();
                countPoints = 0;
            }
            totalPoints++;
            pacManFood++;
        } else if (element instanceof Fruit) {
            countFruitPoints++;
            totalPoints+= (25*countFruitPoints);
            pacManFood++;
        } else if (element instanceof PowerPoint) {
            totalPoints+=10;
            pacManFood++;
        } else if (element instanceof WarpZone) {
            pacMan.checkIfWarp(x, y);
        }
        return element;
    }

    // like a transition but not a really one
    public boolean checkIfWin(){
        return pacManFood >= (mazeControl.getTotalPoints() - 2);
    }
    public void levelUp() {
        pacManLife = 3;
        stopTimer = true;
        level++;
        initGame();
    }
    public void gameOver() {
        stopTimer = true;
        pacManLife--;
        initGame();
    }

}
