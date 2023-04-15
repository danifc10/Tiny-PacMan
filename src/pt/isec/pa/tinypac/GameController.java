package pt.isec.pa.tinypac;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.PacManDraw;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.io.IOException;

public class GameController {
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

    public void movePacMan(int direction) {
        switch (direction) {
            case 2 -> {
                if (pacMan.getY() > 0 && !(mazeControl.checkIfWall(pacMan.getX(), pacMan.getY() - 1))
                        && mazeControl.getXY(pacMan.getX(), pacMan.getY() - 1).getSymbol() != 'Y') {
                    checks(pacMan.getX(), pacMan.getY() - 1);
                    pacMan.setDirection(2);
                    pacMan.move();
                }
                break;
            }
            case 1 -> {
                if (pacMan.getY() < mazeControl.getWidth() && !(mazeControl.checkIfWall(pacMan.getX(), pacMan.getY() + 1))
                        && mazeControl.getXY(pacMan.getX(), pacMan.getY() + 1).getSymbol() != 'Y') {
                    checks(pacMan.getX(), pacMan.getY() + 1);
                    pacMan.setDirection(1);
                    pacMan.move();
                }
                break;
            }
            case 3 -> {
                if (pacMan.getX() > 0 && !(mazeControl.checkIfWall(pacMan.getX() - 1, pacMan.getY()))
                        && mazeControl.getXY(pacMan.getX()  - 1, pacMan.getY()).getSymbol() != 'Y') {
                    checks(pacMan.getX() - 1, pacMan.getY());
                    pacMan.setDirection(3);
                    pacMan.move();
                }
                break;
            }
            case 4 -> {
                if (pacMan.getX() < mazeControl.getHeight() && !(mazeControl.checkIfWall(pacMan.getX() + 1, pacMan.getY()))
                        && mazeControl.getXY(pacMan.getX() + 1, pacMan.getY() ).getSymbol() != 'Y') {
                    checks(pacMan.getX() + 1, pacMan.getY());
                    pacMan.setDirection(4);
                    pacMan.move();
                }
                break;
            }
        }
        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacManDraw());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
    }

    public void update (Terminal terminal) throws IOException {
        /*for (Ghost ghost : ghosts) {
            ghost.move(mazeControl);
            mazeControl.setXY(ghost.getX(), ghost.getY(), new GhostDraw());
        }*/

        KeyStroke key = terminal.readInput();
        movePacMan(readDirection(key));
        mazeControl.setXY(pacMan.getX(), pacMan.getY(), new PacManDraw());
        mazeControl.remove(pacMan.getLastX(), pacMan.getLastY());
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
        }
        return 0;
    }

    public int getLivesPacMan() {
        return pacMan.getLife();
    }
}

