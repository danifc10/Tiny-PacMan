package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class WinState extends GameAdapter {
    protected WinState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.WIN;
    }

    @Override
    public boolean endGame() {
        changeState(new FinalState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean levelUp() {
        int level = context.getLevel();
        context.setLevel(level + 1);
        changeState(new InitialState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }
}
