package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class MovementState extends GameAdapter {
    protected MovementState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public boolean setGhostsFree() { // liberta os fantasmas e passado 5 segundos muda de estado
        //LIBERTA FANTASMAS apos 3 segundos

        for(Ghost ghost : ghosts){
            gameEngine.registerClient(ghost);
        }

        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }


    @Override
    public GameStates getState() {
        return GameStates.MOVEMENT;
    }
}
