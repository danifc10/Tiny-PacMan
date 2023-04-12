package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class InitialState extends GameAdapter {
    public InitialState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context,pacMan, maze, ghosts);
    }

    @Override
    public boolean startGame(){
        changeState(new PlayingState(context, pacMan, maze,ghosts));
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
