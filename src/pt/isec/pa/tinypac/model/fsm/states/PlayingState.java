package pt.isec.pa.tinypac.model.fsm.states;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.data.GhostDraw;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.PacManDraw;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.*;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

import java.io.IOException;

public class PlayingState extends GameAdapter {
    MazeDisplay display;
    public PlayingState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

    @Override
    public void update(Terminal terminal) throws IOException {
        this.display = new MazeDisplay(maze, terminal);
        display.paint(maze);

        for (Ghost ghost : ghosts) {
            ghost.move(maze);
            maze.setXY(ghost.getX(), ghost.getY(), new GhostDraw());
        }

        KeyStroke key = terminal.readInput();
        switch (key.getKeyType()) {
            case ArrowLeft -> {
                if ((pacMan.getY()) > 0 && !(maze.checkIfWall(pacMan.getX(), pacMan.getY() - 1))) {
                    checks(pacMan.getX(), pacMan.getY() - 1);
                    pacMan.setDirection(2);
                    pacMan.move();
                }
                break;
            }
            case ArrowRight -> {
                if (pacMan.getY() < maze.getWidth() && !(maze.checkIfWall(pacMan.getX(), pacMan.getY() + 1))) {
                    checks(pacMan.getX(), pacMan.getY() + 1);
                    pacMan.setDirection(1);
                    pacMan.move();
                }
                break;
            }
            case ArrowUp -> {
                if (pacMan.getX() > 0 && !(maze.checkIfWall(pacMan.getX() - 1, pacMan.getY()))) {
                    checks(pacMan.getX() - 1, pacMan.getY() );
                    pacMan.setDirection(3);
                    pacMan.move();
                }
                break;
            }
            case ArrowDown -> {
                if (pacMan.getX() < maze.getHeight() && !(maze.checkIfWall(pacMan.getX() + 1, pacMan.getY()))) {
                    checks(pacMan.getX() + 1, pacMan.getY() );
                    pacMan.setDirection(4);
                    pacMan.move();
                }
                break;
            }
        }

        maze.setXY(pacMan.getX(), pacMan.getY(), new PacManDraw());
        maze.remove(pacMan.getLastX(), pacMan.getLastY());
        display.paint(maze);
    }

    public void checks(int x, int y){
        if (maze.checkIfPoint(x, y))
            eatPoint();
        if (maze.checkIfPoint(x, y))
            eatPoint();
        else if (maze.checkIfFruit(x, y))
            eatFruit();
        else if (maze.checkIfPower(x, y))
            eatPower();
        else if(maze.checkIfWrap(x, y))
            wrapZone(x, y);
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(10);
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        return true;
    }

    @Override
    public boolean eatPower() {
        // add points
        context.setPoints(50);
        pacMan.powerMode();
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
        return true;
    }

    @Override
    public boolean eatGhost() {
        // add points
        context.setPoints(50);
        //remove ghost
        for(Ghost g : ghosts){
            if(g.getX() == pacMan.getX() && g.getY() == pacMan.getY())
                g.setDead(true);
        }
        return true;
    }

    @Override
    public IGameStates wrapZone(int x, int y) {
        maze.remove(pacMan.getX(), pacMan.getY());
        for(int i = 0; i < maze.getHeight() ; i++){
            for(int j = 0; j < maze.getWidth() ; j++){
                if((j != y && i == x ) && (maze.getXY(i,j).getSymbol() == 'W')){
                    pacMan.setY(j);
                    pacMan.setX(i);

                }
            }
        }
        return null;
    }

    @Override
    public IGameStates eatAll() {
        changeState(new WinState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates ghostCollision() {
        if(pacMan.checkLife())
            restart();
        else
            changeState(new GameOverState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates restart() {
        changeState(new InitialState(context, pacMan, maze, ghosts));
        return null;
    }
}
