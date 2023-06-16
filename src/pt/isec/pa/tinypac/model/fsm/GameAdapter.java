package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
/**
 * adapter for the fsm
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public abstract class GameAdapter implements IGameStates{
    /**
     * Reference to GameContext
     */
    protected GameContext context;
    /**
     * Reference to GameData
     */
    protected GameData gameData;
    /**
     * Reference to MazeControl
     */
    protected MazeControl maze;
    /**
     * Reference to IGameEngine
     */
    protected IGameEngine gameEngine;

    /**
     * Default constructor
     * @param (context, gameData, gameEngine) all the params that adapter needs
     *
     */
    protected GameAdapter(GameContext context, GameData gameData, IGameEngine gameEngine){
        this.context = context;
        this.gameData = gameData;
        this.gameEngine = gameEngine;
    }

    /**
     * change state function
     * @param  newState new state to change
     *
     */
    protected void changeState(GameStates newState) {
        context.changeState(newState.createState(context,gameData, gameEngine));
    }

    /**
     * Get current state
     * @return  null
     */
    @Override
    public GameStates getState() {
        return null;
    }

    /**
     * Transition to pause game
     * @return  false
     */
    @Override
    public boolean pauseGame() {
        return false;
    }

    /**
     * Transition to change PacMan direction
     *
     */
    @Override
    public void changeDirection(Direction direction) {

    }

    @Override
    public void evolve() {

    }

}
