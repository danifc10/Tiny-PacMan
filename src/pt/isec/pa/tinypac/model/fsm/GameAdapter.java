package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;

public class GameAdapter implements IGameStates{
    protected GameContext context;
    protected Ghost [] ghosts;
    protected MazeControl maze;
    protected PacMan pacMan;
    protected IGameEngine gameEngine;

    protected GameAdapter(GameContext context, PacMan pacMan, MazeControl maze, Ghost [] ghosts, IGameEngine gameEngine){
        this.context = context;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
        this.gameEngine = gameEngine;
    }

    protected void changeState(IGameStates newState){
        context.changeState(newState);
    }

    public void checks(){};

    public void setMaze(MazeControl maze){this.maze = maze;}

    @Override
    public boolean setPacManNewDirection(int direction){
        return true;
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
    public boolean startGame(int direction) {
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

    @Override
    public boolean pauseGame() {
        return false;
    }

    @Override
    public boolean resumeGame() {
        return false;
    }
}
