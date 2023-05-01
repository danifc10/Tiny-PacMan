package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class InitialState extends GameAdapter {
    public InitialState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context,pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public boolean startGame(){

        changeState(new MovementState(context, pacMan, maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(10);
        changeState(new InitialState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        changeState(new InitialState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
