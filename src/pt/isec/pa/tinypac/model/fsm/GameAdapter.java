package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.utils.Direction;

public class GameAdapter implements IGameStates{
    protected GameContext context;
    protected GameData gameData;
    protected Ghost [] ghosts;
    protected MazeControl maze;
    protected PacMan pacMan;
    protected IGameEngine gameEngine;

    protected GameAdapter(GameContext context, GameData gameData, IGameEngine gameEngine){
        this.context = context;
        this.gameData = gameData;
        this.gameEngine = gameEngine;
    }

    protected void changeState(GameStates newState) {
        context.changeState(newState.createState(context,gameData, gameEngine));
    }

    public void checks(){};

    public void setMaze(MazeControl maze){this.maze = maze;}

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

    @Override
    public void changeDirection(Direction direction) {
        return;
    }

    @Override
    public void evolve() {

    }
}
