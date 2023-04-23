package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class GameController implements IGameEngineEvolve {
    GameContext fsm;
    MazeControl mazeControl;

    public GameController(MazeControl mazeControl, GameContext context) {
        this.mazeControl = mazeControl;
        this.fsm = context;
    }

    public boolean canMove(int x, int y){
        return !mazeControl.checkIfWall(x, y) && mazeControl.getXY(x, y).getSymbol() != 'Y';
    }
/*
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
*/
    public void checks ( int x, int y){
        if (mazeControl.checkIfPoint(x, y))
            fsm.eatPoint();
        else if (mazeControl.checkIfFruit(x, y))
            fsm.eatFruit();
        else if (mazeControl.checkIfPower(x, y))
            fsm.eatPower();
        else if(mazeControl.checkWin())
            fsm.ifEatAll();
        else if(mazeControl.chekIfGhost(x,y)){
            if(fsm.getState() == GameStates.PLAYING)
                fsm.ghostCollision();
            else
                fsm.eatGhost();
        }
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        //movePacMan();
    }
}

