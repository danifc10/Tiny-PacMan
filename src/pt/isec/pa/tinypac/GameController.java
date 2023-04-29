package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class GameController implements IGameEngineEvolve {
    private IGameEngine gameEngine;
    private GameContext fsm;
    private MazeControl mazeControl;
    private PacMan pacMan;
    private Ghost []ghosts;
    private int countFruitPoints = 0 ;
    private int countGhostsPoints= 0 ;
    private int countPoints= 0 ;
    private int vulnerableTime = 0;
    private int timeStart = 0;

    public GameController(MazeControl mazeControl, IGameEngine gameEngine, PacMan pacMan, Ghost[] ghosts, GameContext context) {
        this.pacMan = pacMan;
        this.gameEngine = gameEngine;
        this.mazeControl = mazeControl;
        this.fsm = context;
        this.ghosts = ghosts;
    }

    public boolean canMove(int x, int y){
        return !mazeControl.checkIfWall(x, y) && mazeControl.getXY(x, y).getSymbol() != 'Y';
    }

    public void movePacMan(){
        int x = pacMan.getX();
        int y = pacMan.getY();
        checks(x, y);
        switch (pacMan.getDirection()) {
            case 2 :
                if (y > 0 && canMove(x, y - 1)) {
                    checks(x, y - 1);
                    pacMan.setDirection(2);
                    pacMan.move();
                }
                break;
            case 1 :
                if (y < mazeControl.getWidth() && canMove(x, y + 1)) {
                    checks(x, y + 1);
                    pacMan.setDirection(1);
                    pacMan.move();
                }
                break;
            case 3 :
                if (x > 0 && canMove(x - 1, y)) {
                    checks(x - 1, y);
                    pacMan.setDirection(3);
                    pacMan.move();
                }
                break;
            case 4 :
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

    public void checks ( int x, int y) {
        if (mazeControl.checkIfPoint(x, y)){
            fsm.eatPoint();
            countPoints++;
            if(countPoints == 20){
                mazeControl.setNewFruit();
                countPoints = 0;
            }
        }
        else if (mazeControl.checkIfFruit(x, y)) {
            fsm.eatFruit();
            countFruitPoints++;
            fsm.setPoints(25*countFruitPoints);
        }
        else if (mazeControl.checkIfPower(x, y)) {
            fsm.eatPower();
        }
        else if(mazeControl.checkWin()) {
            fsm.ifEatAll();
        }
        else if(mazeControl.checkIfWrap(x, y))
            pacMan.checkIfWrap(x, y);
        else if(mazeControl.chekIfGhost(x,y)){
            if(fsm.getState() == GameStates.PLAYING)
                fsm.ghostCollision();
            else if(fsm.getState() == GameStates.VULNERABLE) {
                fsm.eatGhost();
                countGhostsPoints++;
                fsm.setPoints(50*countGhostsPoints);
                if(countGhostsPoints == 4){
                    countGhostsPoints = 0;
                }
            }
        }
    }

    public int getPacManLife(){
        return pacMan.getLife();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

        if(fsm.getState() == GameStates.VULNERABLE) {
            vulnerableTime++;
            if(vulnerableTime == 50){// aproximadamente 5 segundos
                vulnerableTime = 0;
                fsm.endVulnerableTime();
            }
            for(Ghost ghost : ghosts)
                ghost.vulnerableMove();
        }else{
            timeStart++;
            if (timeStart >= 30) {// aproximadamente 5 segundos
                for(Ghost ghost : ghosts)
                    ghost.move();
            }
        }
        movePacMan();
    }

    public void setMaze(MazeControl maze) {
        this.mazeControl = maze;
    }
}

