package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
/**
 * class for win state
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class WinState extends GameAdapter {
    /**
     * Default constructor
     * @param (context, gameData, gameEngine) same as adapter
     *
     */
    public WinState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.WIN;
    }

    @Override
    public void evolve() {

    }

}
