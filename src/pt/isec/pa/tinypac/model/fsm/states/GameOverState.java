package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class GameOverState extends GameAdapter {
    public GameOverState(GameContext context, GameData gameData,IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.GAME_OVER;
    }

    @Override
    public boolean restart() {
        changeState(GameStates.INITIAL);
        return false;
    }
}
