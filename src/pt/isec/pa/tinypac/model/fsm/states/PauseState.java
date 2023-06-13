package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class PauseState extends GameAdapter {
    public PauseState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context,gameData, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.PAUSE;
    }

    @Override
    public void evolve() {

    }

    @Override
    public boolean pauseGame(){
        changeState(gameData.getLastPauseState());
        return true;
    }
}
