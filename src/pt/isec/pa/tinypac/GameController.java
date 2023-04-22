package pt.isec.pa.tinypac;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.PacManDraw;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.io.IOException;

public class GameController implements IGameEngineEvolve {
    GameContext fsm;
    PacMan pacMan;
    Ghost[] ghosts;
    MazeControl mazeControl;

    public GameController(MazeControl mazeControl, GameContext context, PacMan pacMan, Ghost[] ghosts) {
        this.mazeControl = mazeControl;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.fsm = context;
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

        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacManDraw());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
    }

    public void update (Terminal terminal) throws IOException {
        // fantasmas estao a mover e packman tambem
        // le nova direcao
        KeyStroke key = terminal.readInput();

        // verificar direção
        if(readDirection(key) == 1){
            if(canMove(pacMan.getX(), pacMan.getY() + 1))
                pacMan.setDirection(readDirection(key));
        }else if(readDirection(key) == 2){
            if(canMove(pacMan.getX(), pacMan.getY() - 1))
                pacMan.setDirection(readDirection(key));
        }else if(readDirection(key) == 3){
            if(canMove(pacMan.getX() - 1, pacMan.getY()))
                pacMan.setDirection(readDirection(key));
        }else if(readDirection(key) == 4){
            if (canMove(pacMan.getX() + 1, pacMan.getY()))
                pacMan.setDirection(readDirection(key));
        }else if(readDirection(key) == 5){
            fsm.pauseGame();
        }

    }

    public void checks ( int x, int y){
        if (mazeControl.checkIfPoint(x, y))
            fsm.eatPoint();
        else if (mazeControl.checkIfFruit(x, y))
            fsm.eatFruit();
        else if (mazeControl.checkIfPower(x, y))
            fsm.eatPower();
        else if (mazeControl.checkIfWrap(x, y))
            wrapZone(x, y);
        else if(mazeControl.checkWin())
            fsm.ifEatAll();
        else if(mazeControl.chekIfGhost(x,y)){
            if(fsm.getState() == GameStates.PLAYING)
                fsm.ghostCollision();
            else
                fsm.eatGhost();
        }
    }

    public void wrapZone(int x, int y){
        mazeControl.remove(pacMan.getX(), pacMan.getY());
        for(int i = 0; i < mazeControl.getHeight() ; i++){
            for(int j = 0; j < mazeControl.getWidth() ; j++){
                if((j != y && i == x ) && (mazeControl.getXY(i,j).getSymbol() == 'W')){
                    pacMan.setY(j);
                    pacMan.setX(i);
                }
            }
        }
    }

    public int readDirection(KeyStroke key){
        switch (key.getKeyType()) {
            case ArrowLeft -> {
                return 2;
            }
            case ArrowRight -> {
                return 1;
            }
            case ArrowUp -> {
                return 3;
            }
            case ArrowDown -> {
                return 4;
            }
            case Character ->{
                return 5;
            }
        }
        return 0;
    }

    public int getLivesPacMan() {
        return pacMan.getLife();
    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        movePacMan();
    }
}

