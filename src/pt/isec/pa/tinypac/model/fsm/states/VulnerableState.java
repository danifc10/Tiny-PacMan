package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.data.PowerPoint;
import pt.isec.pa.tinypac.model.data.elements.Blinky;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class VulnerableState extends GameAdapter {
    private int vulnerableTime = (20-gameData.getLevel()) ; // diminui conforme aumenta de nivel
    private int countGhosts = 0;
    public VulnerableState(GameContext context, GameData gameData, IGameEngine gameEngine) {
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
        else if(eaten instanceof Blinky) {
            countGhosts++;
            gameData.eatGhost(countGhosts);
        }

        if(gameData.vulnerableMoveGhosts()){
            countGhosts++;
            gameData.eatGhost(countGhosts);
        }

        if(gameData.checkIfWin()) {
            gameData.levelUp();
            changeState(GameStates.INITIAL);
        }
        if (countGhosts== 4)
            countGhosts = 0;
    }

    @Override
    public GameStates getState() {
        return GameStates.VULNERABLE;
    }

}
