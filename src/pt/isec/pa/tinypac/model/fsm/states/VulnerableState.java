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
 * class for vulnerable state
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class VulnerableState extends GameAdapter {
    private int vulnerableTime = (60 - (gameData.getLevel() * 2));

    /**
     * Default constructor
     * @param (context, gameData, gameEngine) same as adapter
     *
     */
    public VulnerableState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public void changeDirection(Direction direction) {
        gameData.setPacManDirection(direction);
    }

    @Override
    public boolean pauseGame() {
        gameData.setStateOfPause(GameStates.VULNERABLE);
        changeState(GameStates.PAUSE);
        return true;
    }

    @Override
    public void evolve() {
        vulnerableTime--;
        gameData.setVulnerable();
        if(vulnerableTime <= 0) {
            gameData.endVulnerable();
            changeState(GameStates.PLAYING);
        }

        // se comer powerDot dentro do vulneravel o tempo fica a dobrar
        IMazeElement eaten = gameData.movePacMan();
        if(eaten instanceof PowerPoint)
            vulnerableTime = vulnerableTime * 2;
        else if(eaten instanceof Blinky || eaten instanceof Clyde || eaten instanceof Pinky || eaten instanceof Inky){
            gameData.eatGhost(eaten);
        }

        gameData.vulnerableMoveGhosts();

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
        return GameStates.VULNERABLE;
    }

}
