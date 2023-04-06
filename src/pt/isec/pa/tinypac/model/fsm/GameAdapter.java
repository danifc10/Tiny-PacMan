package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.elements.Ghost;
import pt.isec.pa.tinypac.model.fsm.elements.PacMan;

public class GameAdapter implements IGameStates{


    protected GameContext context;
    protected Ghost [] ghosts;
    protected Maze maze;
    protected PacMan pacMan;

    protected GameAdapter(GameContext context, PacMan pacMan, Maze maze, Ghost [] ghosts){
        this.context = context;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
    }

    protected void changeState(IGameStates newState){
        context.changeState(newState);
    }

    @Override
    public GameStates getState() {
        return null;
    }

    @Override
    public boolean startGame() {
        return false;
    }

    @Override
    public IGameStates eatPoint() {
        return null;
    }

    @Override
    public IGameStates eatFruit() {
        return null;
    }

    @Override
    public IGameStates eatPower() {
        return null;
    }

    @Override
    public IGameStates eatGhost() {
        return null;
    }

    @Override
    public IGameStates eatPoint(int x, int y) {
        return null;
    }

    @Override
    public IGameStates wrapZone() {
        return null;
    }

    @Override
    public IGameStates eatAll() {
        return null;
    }

    @Override
    public IGameStates ghostCollision() {
        return null;
    }

    @Override
    public IGameStates restart() {
        return null;
    }

    @Override
    public IGameStates levelUp() {
        return null;
    }
}
