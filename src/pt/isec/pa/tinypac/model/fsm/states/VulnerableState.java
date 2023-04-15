package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class VulnerableState extends GameAdapter {
    private int counter  = 10;
    private boolean counterOn = false;
    protected VulnerableState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts) {
        super(context, pacMan, maze, ghosts);
        this.counterOn = true;
    }

    @Override
    public GameStates getState() {
        return GameStates.VULNERABLE;
    }

    @Override
    public boolean endVulnerableTime() {
        changeState(new PlayingState(context, pacMan, maze, ghosts));
        return true;
    }

    @Override
    public boolean eatGhost() { // se comer um fantasma
        // add points
        context.setPoints(50);
        // remove ghost
        for(Ghost g : ghosts){
            if(g.getX() == pacMan.getX() && g.getY() == pacMan.getY())
                g.setDead(true);
        }
        changeState(new VulnerableState(context, pacMan, maze, ghosts));
        return true;
    }

}
