package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class PlayingState extends GameAdapter {

    public PlayingState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

    @Override
    public boolean pauseGame() {
        changeState(new PauseState(context,pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(1);
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPower() { // se come poderes passa para o estado vulneravel
        context.setPoints(10);
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }

        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean ghostCollision() {  // se colide com fantasma passa para o estado initial
        changeState(new GameOverState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean ifEatAll() { //tanto comer todos os fantasmas como todos os pontos
        changeState(new WinState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

}
