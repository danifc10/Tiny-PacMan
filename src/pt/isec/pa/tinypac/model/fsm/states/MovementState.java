package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class MovementState extends GameAdapter {
    protected MovementState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public boolean setGhostsFree() { // liberta os fantasmas e passado 5 segundos muda de estado
        for(Ghost ghost : ghosts)
            ghost.move();

        System.out.println("leaving movement state");
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean setPacManNewDirection(int direction){
        if(direction == 1){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() + 1))
                pacMan.setDirection(1);
        }else if(direction == 2){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() - 1))
                pacMan.setDirection(2);
        }else if(direction== 3){
            if(pacMan.canMove(pacMan.getX() - 1, pacMan.getY()))
                pacMan.setDirection(3);
        }else if(direction== 4){
            if (pacMan.canMove(pacMan.getX() + 1, pacMan.getY()))
                pacMan.setDirection(4);
        }
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(10);
        changeState(new MovementState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        changeState(new MovementState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public GameStates getState() {
        return GameStates.MOVEMENT;
    }
}
