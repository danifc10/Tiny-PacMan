package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class WinState extends GameAdapter {
    protected WinState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }

    @Override
    public GameStates getState() {
        return GameStates.WIN;
    }

    @Override
    public boolean endGame() {
        changeState(new FinalState(context, pacMan, maze, ghosts));
        return false;
    }

    @Override
    public boolean levelUp() {
        int level = context.getLevel();
        context.setLevel(level + 1);
        changeState(new InitialState(context, pacMan, maze, ghosts));
        return true;
    }
}
