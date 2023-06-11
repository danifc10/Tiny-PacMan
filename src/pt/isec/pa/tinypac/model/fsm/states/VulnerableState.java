package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.PowerPoint;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;

public class VulnerableState extends GameAdapter {
    private int vulnerableTime = (40 -gameData.getLevel()) ; // diminui conforme aumenta de nivel
    private int countGhosts = 0;
    public VulnerableState(GameContext context, GameData gameData, IGameEngine gameEngine) {
        super(context, gameData, gameEngine);
    }

    @Override
    public void changeDirection(Direction direction) {
        gameData.setPacManDirection(direction);
    }

    @Override
    public boolean pauseGame() {
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
        IMazeElement eaten = gameData.movePacMan();
        if(eaten instanceof PowerPoint)
            vulnerableTime = vulnerableTime * 2;

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
