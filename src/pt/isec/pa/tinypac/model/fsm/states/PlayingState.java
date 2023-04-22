package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

public class PlayingState extends GameAdapter {
    MazeDisplay display;
    public PlayingState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

    @Override
    public boolean pauseGame() {
        gameEngine.pause();
        changeState(new PauseState(context,pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(10);
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPower() { // se come poderes passa para o estado vulneravel
        // add points
        context.setPoints(50);
        pacMan.powerMode();
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }


    @Override
    public boolean ghostCollision() {  // se colide com fantasma passa para o estado gameover
        pacMan.setLife();
        changeState(new GameOverState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean ifEatAll() {
        changeState(new WinState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }
}
