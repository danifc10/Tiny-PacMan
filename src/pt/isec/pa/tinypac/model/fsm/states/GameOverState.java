package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.Position;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.io.FileNotFoundException;
import java.util.List;

public class GameOverState extends GameAdapter {
    protected GameOverState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.GAME_OVER;
    }

    @Override
    public boolean restart() {
        System.out.println("aqui2");
        this.gameEngine.stop();
        this.gameEngine = new GameEngine();
        String file = context.getFileLevel();
        try {
            this.maze = new MazeControl(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        context.setMaze(maze);
        pacMan.setMaze(maze);

        context.setPoints(0);
        pacMan.setX(maze.getXghostStart());
        pacMan.setY(maze.getYghostStart());
        for(Ghost ghosts : ghosts){
            ghosts.setMaze(maze);
        }
        List<Position> positions = maze.getGhostStartPositions();
        int i = 0;
        for(Ghost ghosts : ghosts){
            ghosts.setMaze(maze);
            ghosts.setX(positions.get(i).getX());
            ghosts.setY(positions.get(i).getY());
            i++;
        }
        changeState(new InitialState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean endGame() {
        changeState(new FinalState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }
}
