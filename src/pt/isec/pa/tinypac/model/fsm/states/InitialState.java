package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.model.fsm.elements.Ghost;
import pt.isec.pa.tinypac.model.fsm.elements.PacMan;


public class InitialState extends GameAdapter {
    public InitialState(GameContext context,PacMan pacMan, Maze maze, Ghost[] ghosts) {
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
