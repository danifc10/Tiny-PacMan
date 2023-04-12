package pt.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;

import java.io.IOException;

public class GameAdapter implements IGameStates{


    protected GameContext context;
    protected Ghost [] ghosts;
    protected MazeControl maze;
    protected PacMan pacMan;

    protected GameAdapter(GameContext context, PacMan pacMan, MazeControl maze, Ghost [] ghosts){
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
    public void update(Terminal terminal) throws IOException {
        return;
    }

    @Override
    public boolean startGame() {
        return false;
    }

    @Override
    public boolean eatPoint() {
        return true;
    }

    @Override
    public boolean eatFruit() {
        return true;
    }

    @Override
    public boolean eatPower() {
        return true;
    }

    @Override
    public boolean eatGhost() {
        return true;
    }

    @Override
    public IGameStates eatPoint(int x, int y) {
        return null;
    }

    @Override
    public IGameStates wrapZone(int x, int y) {
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
