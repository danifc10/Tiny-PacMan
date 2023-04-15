package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import static java.lang.Thread.sleep;

public class MovementState extends GameAdapter {
    protected MovementState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
    }

    @Override
    public boolean setGhostsFree() { // liberta os fantasmas e passado 5 segundos muda de estado
        //LIBERTA FANTASMAS apos 3 segundos
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(Ghost ghost : ghosts){
            ghost.setTargetX(maze.getXghostStart());
            ghost.setTargetY(maze.getYghostStart());
            ghost.move(maze);
        }
        changeState(new PlayingState(context, pacMan,maze,ghosts));
        return true;
    }
    @Override
    public GameStates getState() {
        return GameStates.MOVEMENT;
    }
}
