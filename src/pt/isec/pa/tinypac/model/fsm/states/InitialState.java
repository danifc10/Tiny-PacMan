package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class InitialState extends GameAdapter {
    private int startTime;
    private int aux = 0;
    public InitialState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context,gameData, gameEngine);
        aux = 0;
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
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
