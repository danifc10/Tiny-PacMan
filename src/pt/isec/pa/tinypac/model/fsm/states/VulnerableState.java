package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.util.ArrayList;

public class VulnerableState extends GameAdapter {
    protected VulnerableState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);

    }

    @Override
    public GameStates getState() {
        return GameStates.VULNERABLE;
    }

    @Override
    public boolean ifEatAll() { //tanto comer todos os fantasmas como todos os pontos
        changeState(new WinState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean endVulnerableTime() {
        for (Ghost g : ghosts) {
            g.setVulnerable(false);
            g.roadMade = new ArrayList<>();
            g.road_index =0;
        }
        changeState(new PlayingState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatGhost() { // se comer um fantasma
        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(10);
        changeState(new VulnerableState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        // add points
        context.setPoints(20);
        changeState(new VulnerableState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPower() {
        // add points
        context.setPoints(50);
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }

        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }
}
