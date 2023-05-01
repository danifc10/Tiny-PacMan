package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.PowerPoint;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Position;

import java.util.List;

public class GameController implements IGameEngineEvolve {
    private GameContext fsm;
    private MazeControl mazeControl;
    private PacMan pacMan;
    private Ghost[] ghosts;
    private int countFruitPoints = 0;
    private int countGhostsPoints = 0;
    private int countPoints = 0;
    private int vulnerableTime = 0;
    private int timeStart = 0;
    private int pacManLife = 3;
    private int numOfGhosts = 4;
    private int level = 1;

    public GameController(MazeControl mazeControl, PacMan pacMan, Ghost[] ghosts, GameContext context) {
        this.pacMan = pacMan;
        this.mazeControl = mazeControl;
        this.fsm = context;
        this.ghosts = ghosts;
    }

    public boolean canMove(int x, int y) {
        return !mazeControl.checkIfWall(x, y) && mazeControl.getXY(x, y).getSymbol() != 'Y';
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

    public void checks(int x, int y) {
        if (mazeControl.checkIfPoint(x, y)) {
            fsm.eatPoint();
            countPoints++;
            if (countPoints == 20) {
                mazeControl.setNewFruit();
                countPoints = 0;
            }
        } else if (mazeControl.checkIfFruit(x, y)) {
            fsm.eatFruit();
            countFruitPoints++;
            fsm.setPoints(25 * countFruitPoints);
        } else if (mazeControl.checkIfPower(x, y)) {
            if(fsm.getState() == GameStates.MOVEMENT || fsm.getState() == GameStates.VULNERABLE || fsm.getState() == GameStates.INITIAL)
                mazeControl.setXY(x, y, new PowerPoint());
            else
                fsm.eatPower();
        } else if (mazeControl.checkWin() || numOfGhosts == 0) {
            fsm.ifEatAll();
        } else if (mazeControl.checkIfWarp(x, y))
            pacMan.checkIfWarp(x, y);
        else if (mazeControl.chekIfGhost(x, y)) {
            if (fsm.getState() == GameStates.PLAYING) {
                fsm.ghostCollision();
            } else if (fsm.getState() == GameStates.VULNERABLE) {
                fsm.eatGhost();
                countGhostsPoints++;
                numOfGhosts--;
                fsm.setPoints(50 * countGhostsPoints);
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
            }
        }
    }

    public void restartMaze() {
        numOfGhosts= 4;
        this.mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
        for(Ghost ghost : ghosts){
            mazeControl.remove(ghost.getLastX(), ghost.getLastY());
            mazeControl.remove(ghost.getX(), ghost.getY());
        }

        MazeControl mazeControl = new MazeControl(fsm.getLevel());
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
        fsm.resetPoints();
    }

    public void levelUp() {
        numOfGhosts= 4;
        level = fsm.getLevel();
        pacManLife = 3;
        fsm.setLevel(level + 1);
        MazeControl mazeControl = new MazeControl(fsm.getLevel());
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
        fsm.resetPoints();
    }

    public void setPacManNewDirection(int direction){
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

    public int getPacManLife() {
        return pacManLife;
    }

    public void setMaze(MazeControl maze) {
        this.mazeControl = maze;
    }

    public MazeControl getMaze() {
        return this.mazeControl;
    }

    public int getLevel(){
        return level;
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if (fsm.getState() == GameStates.VULNERABLE) {
            vulnerableTime++;
            if (vulnerableTime == 50) {// aproximadamente 5 segundos
                vulnerableTime = 0;
                fsm.endVulnerableTime();
            }
            for (Ghost ghost : ghosts)
                if(!ghost.isDead()) {
                    ghost.vulnerableMove();
                }
        } else {
            timeStart++;
            if (timeStart >= 30) {// aproximadamente 5 segundos
                for (Ghost ghost : ghosts) {
                    if(!ghost.isDead())
                        ghost.move();
                }

                fsm.setGhostsFree();
            }
        }
        movePacMan();
    }

}

