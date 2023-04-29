package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameAdapter;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Position;
import java.util.List;

public class PlayingState extends GameAdapter {

    public PlayingState(GameContext context, PacMan pacMan, MazeControl maze, Ghost[] ghosts, IGameEngine gameEngine) {
        super(context, pacMan, maze, ghosts, gameEngine);
    }

    @Override
    public GameStates getState() {
        return GameStates.PLAYING;
    }

    @Override
    public boolean pauseGame() {
        changeState(new PauseState(context,pacMan, maze, ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPoint() {
        // add points
        context.setPoints(1);
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatFruit() {
        changeState(new PlayingState(context, pacMan,maze,ghosts, gameEngine));
        return true;
    }

    @Override
    public boolean eatPower() { // se come poderes passa para o estado vulneravel
        context.setPoints(10);
        for(Ghost g : ghosts){
            g.setVulnerable(true);
          //  g.getToJail();
        }

        changeState(new VulnerableState(context, pacMan, maze, ghosts, gameEngine));
        return true;
    }


    @Override
    public boolean ghostCollision() {  // se colide com fantasma passa para o estado gameover
        pacMan.setLife();
        try {
            maze = new MazeControl("Level101.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        maze.remove(pacMan.getLastX(), pacMan.getLastY());
        pacMan.setY(maze.getYstart());
        pacMan.setX(maze.getXstart());
        pacMan.setMaze(maze);
        List<Position> positions = maze.getGhostStartPositions();
        int i = 0;
        for(Ghost g : ghosts){
            maze.remove(g.getLastX(), g.getLastY());
            g.setMaze(maze);
            g.setTargetX(maze.getXghostStart());
            g.setTargetY(maze.getYghostStart());
            g.setX(positions.get(i).getX());
            g.setY(positions.get(i).getY());
            i++;
        }
        context.setMaze(maze);
        context.setPoints(0);
        changeState(new InitialState(context, pacMan, maze, ghosts, gameEngine));
        //changeState(new PlayingState(context, pacMan, maze, ghosts, gameEngine));
        return false;
    }

    @Override
    public boolean ifEatAll() {
        changeState(new WinState(context, pacMan, maze, ghosts, gameEngine));
        return false;
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

}
