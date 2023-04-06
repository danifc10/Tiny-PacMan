package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;

public class GameOverState extends GameAdapter {
    protected GameOverState(GameContext context, PacMan pacMan, Maze maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }

}
