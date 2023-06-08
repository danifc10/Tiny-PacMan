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
        else if(eaten instanceof Blinky) { // devia ser GHOST
            if(gameData.getPacManLife() > 0) {
                System.out.println("no more lifes");
                gameData.setLife();
                gameData.initGame();
                changeState(GameStates.INITIAL);
            }else
                changeState(GameStates.GAME_OVER);
        }
        gameData.moveGhosts();
        if(gameData.checkIfWin()) {
            gameData.levelUp();
            changeState(GameStates.INITIAL);
        }
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

}
