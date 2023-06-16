package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.states.*;
/**
 * enum for all states
 * @author Daniela Correia
 *
 */
public enum GameStates {
    INITIAL,GAME_OVER,PLAYING, WIN, VULNERABLE, PAUSE;

    /**
     * Create a new state
     * @param (context, data) all the things that states need
     */
    public IGameStates createState(GameContext context, GameData data, IGameEngine gameEngine) {
        return switch (this) {
            case INITIAL -> new InitialState(context,data,gameEngine);
            case PLAYING -> new PlayingState(context,data, gameEngine);
            case VULNERABLE -> new VulnerableState(context,data,gameEngine);
            case WIN -> new WinState(context,data,gameEngine);
            case GAME_OVER -> new GameOverState(context, data,gameEngine);
            case PAUSE -> new PauseState(context, data,gameEngine);
            //default -> null;
        };
    }
}
