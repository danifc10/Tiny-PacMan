package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.elements.Ghost;
import pt.isec.pa.tinypac.model.fsm.elements.PacMan;

public class WinState extends GameAdapter {
    protected WinState(GameContext context, PacMan pacMan, Maze maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }
}
