package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.IGameStates;
import pt.isec.pa.tinypac.model.fsm.elements.Ghost;
import pt.isec.pa.tinypac.model.fsm.elements.PacMan;


public class PlayingState extends GameAdapter {

    public PlayingState(GameContext context,PacMan pacMan, Maze maze, Ghost[] ghosts) {
        super(context,pacMan, maze, ghosts);
        update();
    }

    public void update(){
        pacMan.move();

        for(Ghost ghosts : ghosts) {
            ghosts.move();
        }

        if(maze.checkIfFruit(pacMan.getX(),pacMan.getX())){
            eatFruit();
        }else if(maze.checkIfPoint(pacMan.getX(), pacMan.getY())){
            eatPoint();
        }else if(maze.checkIfPower(pacMan.getX(), pacMan.getY())){
            eatPower();
        }else if(maze.checkWin()){
            eatAll();
        }
        // ghost colision
    }

    @Override
    public IGameStates eatPoint() {
        // add points
        context.setPoints(10);
        //remove dot
        maze.remove(pacMan.getX(), pacMan.getY());
        changeState(new PlayingState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates eatFruit() {
        // add points
        context.setPoints(20);
        //remove dot
        maze.remove(pacMan.getX(), pacMan.getY());
        changeState(new PlayingState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates eatPower() {
        // add points
        context.setPoints(50);
        //remove dot
        maze.remove(pacMan.getX(), pacMan.getY());
        pacMan.powerMode();
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
        changeState(new PlayingState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates eatGhost() {
        // add points
        context.setPoints(50);
        //remove ghost
        for(Ghost g : ghosts){
            if(g.getX() == pacMan.getX() && g.getY() == pacMan.getY())
                g.setDead(true);
        }
        changeState(new PlayingState(context, pacMan, maze, ghosts));
        return null;
    }

    @Override
    public IGameStates wrapZone() {
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
