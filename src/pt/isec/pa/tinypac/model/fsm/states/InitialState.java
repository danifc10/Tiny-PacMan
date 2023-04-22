package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
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
    public boolean startGame(int direction){
        pacMan.setDirection(direction);
        gameEngine.start(180);
        changeState(new MovementState(context, pacMan, context.getMaze(),ghosts, gameEngine));
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
