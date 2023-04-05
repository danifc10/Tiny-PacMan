package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;

public class GameContext {
    private IGameState state; // current state
    private Ghost [] ghosts;
    private PacMan pacMan;
    private Maze maze;

    public GameContext(){

    }

    public GameStates getState(){
        return state.getState();
    }

    //transições
    public void update(){

        switch (getState()){
            case INITIAL:
                break;
            case EATING_FRUIT:
                break;
            case PLAYING:
                break;
            case GAME_OVER:
                break;
            case LEVEL_UP:
                break;
            case WRAP_ZONE:
                break;
            case EATING_GHOST:
                break;
            case EATING_POINT:
                break;
            case EATING_POWER:
                break;
            case VULNERABILITY:
                break;
            case GHOST_COLISION:
                break;
        }
    }
}
