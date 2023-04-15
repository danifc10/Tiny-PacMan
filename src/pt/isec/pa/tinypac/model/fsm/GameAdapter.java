package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;

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
    public boolean endVulnerableTime() {
        return false;
    }

    @Override
    public boolean setGhostsFree() {
        return false;
    }

    @Override
    public boolean checkIfGhostsOut() {
        return false;
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
    public boolean wrapZone(int x, int y) {
        return false;
    }

    @Override
    public boolean ifEatAll() {
        return false;
    }

    @Override
    public boolean ghostCollision() {
        return false;
    }

    @Override
    public boolean restart() {
        return false;
    }

    @Override
    public boolean levelUp() {
        return false;
    }

    @Override
    public boolean endGame() {
        return false;
    }
}
