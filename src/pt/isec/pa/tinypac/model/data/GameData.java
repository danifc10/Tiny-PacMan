package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class GameData implements IGameEngineEvolve {
    private MazeControl mazeControl;
    private PacMan pacMan;
    private Ghost [] ghosts;
    private List<Position> positions;
    private int points = 0;
    private int level;
    private int countFruitPoints = 0;
    private int countGhostsPoints = 0;
    private int countPoints = 0;
    private int vulnerableTime = 0;
    private int timeStart = 0;
    private int pacManLife = 3;
    private int numOfGhosts = 4;

    public GameData() {
        //initGame();
    }

    public void initGame() {
        mazeControl = new MazeControl(1);
        positions = mazeControl.getGhostStartPositions();
        pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), 1, mazeControl);
        ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), 2,  mazeControl),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), 2,  mazeControl),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), 1,  mazeControl),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), 2, mazeControl)
        };
    }

    public void setPoints(int i) {
        points = i;
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

    public int getPoints() {
        return  countPoints;
    }

    public int getLevel() {
        return level;
    }

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

    public void setLevel(int i) {
        level = i;
    }

    public void levelUp() {
        numOfGhosts= 4;
        pacManLife = 3;
        level++;
        MazeControl mazeControl = new MazeControl(level);
        List<Position> positions = mazeControl.getGhostStartPositions();
        PacMan pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), 1, mazeControl);

        Ghost[] ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), 2,  mazeControl),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), 2, mazeControl),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), 1, mazeControl),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), 2,  mazeControl)
        };
        this.mazeControl = mazeControl;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        points = 0;
    }

    public void restartMaze() {
        numOfGhosts= 4;
        this.mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
        for(Ghost ghost : ghosts){
            mazeControl.remove(ghost.getLastX(), ghost.getLastY());
            mazeControl.remove(ghost.getX(), ghost.getY());
        }

        MazeControl mazeControl = new MazeControl(level);
        List<Position> positions = mazeControl.getGhostStartPositions();
        PacMan pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), 1, mazeControl);

        Ghost[] ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), 2,mazeControl),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), 2, mazeControl),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), 1, mazeControl),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), 2, mazeControl)
        };
        this.mazeControl = mazeControl;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        pacManLife--;
    }

    public void movePacMan() {
        int x = pacMan.getX();
        int y = pacMan.getY();
        checks(x, y);
        switch (pacMan.getDirection()) {
            case 2:
                if (y > 0 && canMove(x, y - 1)) {
                    checks(x, y - 1);
                    pacMan.setDirection(2);
                    pacMan.move();
                }
                break;
            case 1:
                if (y < mazeControl.getWidth() && canMove(x, y + 1)) {
                    checks(x, y + 1);
                    pacMan.setDirection(1);
                    pacMan.move();
                }
                break;
            case 3:
                if (x > 0 && canMove(x - 1, y)) {
                    checks(x - 1, y);
                    pacMan.setDirection(3);
                    pacMan.move();
                }
                break;
            case 4:
                if (x < mazeControl.getHeight() && canMove(x + 1, y)) {
                    checks(x + 1, y);
                    pacMan.setDirection(4);
                    pacMan.move();
                }
                break;
        }

        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacMan());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
    }

    public boolean canMove(int x, int y) {
        return !mazeControl.checkIfWall(x, y) && mazeControl.getXY(x, y).getSymbol() != 'Y';
    }

    public void checks(int x, int y) {
        if (mazeControl.checkIfPoint(x, y)) {
            //fsm.eatPoint();
            countPoints++;
            if (countPoints == 20) {
                mazeControl.setNewFruit();
                countPoints = 0;
            }
        } else if (mazeControl.checkIfFruit(x, y)) {
           // fsm.eatFruit();
            countFruitPoints++;
            //fsm.setPoints(25 * countFruitPoints);
        } else if (mazeControl.checkIfPower(x, y)) {

        } else if (mazeControl.checkWin() || numOfGhosts == 0) {
            //fsm.ifEatAll();
        } else if (mazeControl.checkIfWarp(x, y))
            pacMan.checkIfWarp(x, y);
        else if (mazeControl.chekIfGhost(x, y)) {
            /*
            if (fsm.getState() == GameStates.PLAYING) {
               // fsm.ghostCollision();
            } else if (fsm.getState() == GameStates.VULNERABLE) {
              //  fsm.eatGhost();
                countGhostsPoints++;
                numOfGhosts--;
                points = 50 * countGhostsPoints;
                for(Ghost g : ghosts){
                    if((g.getX() == x && g.getY() == y )||( g.getLastX() == x && g.getLastY() == y)) {
                        g.setDead(true);
                    }
                }
                for(Ghost g : ghosts){
                    if(g.isDead())
                        mazeControl.removeGhost(g.getX(), g.getY());
                }
                if (countGhostsPoints == 4)
                    countGhostsPoints = 0;
            }*/
        }
    }

    public char[][] getMaze() {
        return mazeControl.getMazeControl();
    }

    public void setGhostsFree() {
        for(Ghost g: ghosts){
            g.move();
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        timeStart++;
        if (timeStart >= 10) {// aproximadamente 5 segundos
            for (Ghost ghost : ghosts) {
                if(!ghost.isDead())
                    ghost.move();
            }
            setGhostsFree();
        }

        movePacMan();
        System.out.println(pacMan.getX() + " " + pacMan.getY());
    }

    public int getPacManLife() {
        return pacManLife;
    }

    public int getTime() {
        return timeStart;
    }
}
