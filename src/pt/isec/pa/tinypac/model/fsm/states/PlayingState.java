package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.PowerPoint;
import pt.isec.pa.tinypac.model.data.elements.Blinky;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
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
        changeState(GameStates.PAUSE);
        return true;
    }

    public void evolve(){
        IMazeElement eaten = gameData.movePacMan();
        if(eaten instanceof PowerPoint)
            changeState(GameStates.VULNERABLE);
        else if(eaten instanceof Blinky) {
            if(gameData.getPacManLife() > 0) {
                gameData.setLife();
                gameData.initGame();
                changeState(GameStates.INITIAL);
            }else
                changeState(GameStates.GAME_OVER);
        }
        gameData.moveGhosts();
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }




}
