package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;

public class FinalState extends GameAdapter {
    protected FinalState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }
}
