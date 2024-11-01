package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;
/**
 * class for initial state
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class InitialState extends GameAdapter {
    private int aux;
    /**
     * Default constructor
     * @param (context, gameData, gameEngine) same as adapter
     *
     */
    public InitialState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context,gameData, gameEngine);
        aux = 0;
    }

    @Override
    public void changeDirection(Direction direction) {
        gameData.setPacManDirection(direction);
        aux = 1;
    }

    @Override
    public void evolve() {
        if(gameData.getTime() >= 5 && aux == 1) {
            gameData.setGhostsFree();
            changeState(GameStates.PLAYING);
        }
        if(aux == 1){
            gameData.movePacMan();
        }
    }

    @Override
    public boolean pauseGame() {
        gameData.setStateOfPause(GameStates.INITIAL);
        changeState(GameStates.PAUSE);
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
