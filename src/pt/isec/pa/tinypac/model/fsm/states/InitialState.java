package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class InitialState extends GameAdapter {
    public InitialState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context,gameData, gameEngine);
    }

    @Override
    public boolean startGame(){
        System.out.println("initial start");
        gameData.initGame();
        changeState(GameStates.INITIAL);
        return true;
    }

    @Override
    public void changeDirection(int direction) {
        System.out.println("aqui");
        gameData.setPacManDirection(direction);
        gameEngine.registerClient(gameData);
        gameEngine.start(900);
        changeState(GameStates.PLAYING);
    }


    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
