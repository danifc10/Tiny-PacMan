package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.elements.PowerPoint;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;
/**
 * class for playing state
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class PlayingState extends GameAdapter {
    /**
     * Default constructor
     * @param (context, gameData, gameEngine) same as adapter
     *
     */
    public PlayingState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public void changeDirection(Direction direction) {
        gameData.setPacManDirection(direction);
    }

    @Override
    public boolean pauseGame() {
        gameData.setStateOfPause(GameStates.PLAYING);
        changeState(GameStates.PAUSE);
        return true;
    }

    public void evolve(){
        IMazeElement eaten = gameData.movePacMan();

        // se comer powerDot ou se colidir com um fantasma
        if(eaten instanceof PowerPoint)
            changeState(GameStates.VULNERABLE);
        else if(eaten instanceof Blinky || eaten instanceof Clyde || eaten instanceof Pinky || eaten instanceof Inky) {
            if(gameData.getPacManLife() > 0) {
                gameData.gameOver();
                changeState(GameStates.INITIAL);
            }else
                changeState(GameStates.GAME_OVER);
        }

        // Se algum fantasma comer o pacman retorna true
        if(gameData.moveGhosts()) {
            if (gameData.getPacManLife() > 0 ) {
                gameData.gameOver();
                changeState(GameStates.INITIAL);
            } else
                changeState(GameStates.GAME_OVER);
        }

        // se o jogo tiver ganho (se nao houver mais pontos para comer ) ou se já nao houver fantasmas
        if(gameData.checkIfWin() || gameData.getNumGhosts() <= 0) {
            if(gameData.getLevel() < 20) {
                gameData.levelUp();
                changeState(GameStates.INITIAL);
            }else{
                changeState(GameStates.WIN);
            }
        }
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

}
