package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class PlayingState extends GameAdapter {

    public PlayingState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public void changeDirection(int direction) {
        gameData.setPacManDirection(direction);
    }

    @Override
    public boolean pauseGame() {
        gameEngine.unregisterClient(gameData);
        changeState(GameStates.PAUSE);
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }




}
