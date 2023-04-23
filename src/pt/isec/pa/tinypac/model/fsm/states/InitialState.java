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
        gameEngine.registerClient(pacMan);
        gameEngine.start(180);
        changeState(new MovementState(context, pacMan, context.getMaze(),ghosts, gameEngine));
        return true;
    }
    @Override
    public boolean eatPoint() {
        System.out.println("entrei pontos");
        // add points
        context.setPoints(10);
        changeState(new InitialState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        changeState(new InitialState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPower() { // se come poderes passa para o estado vulneravel
        // add points
        context.setPoints(50);
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.INITIAL;
    }
}
