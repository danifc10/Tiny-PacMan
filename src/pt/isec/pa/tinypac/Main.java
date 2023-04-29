package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.text.GameUI;
import pt.isec.pa.tinypac.utils.Position;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        MazeControl mazeControl = new MazeControl("Level101.txt");
        List<Position> positions = mazeControl.getGhostStartPositions();
        PacMan pacMan = new PacMan(mazeControl.getXstart(), mazeControl.getYstart(), 1,1, mazeControl);

        Ghost[] ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), 2, 1, mazeControl),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), 2, 1, mazeControl),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), 1, 1, mazeControl),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), 2, 1, mazeControl)
        };

        IGameEngine gameEngine = new GameEngine();

        GameContext fsm = new GameContext(mazeControl, gameEngine, pacMan, ghosts);
        GameController game = new GameController(mazeControl, gameEngine, pacMan, ghosts,fsm);
        GameUI ui = new GameUI(mazeControl, fsm, game, gameEngine);
        gameEngine.registerClient(game);
        gameEngine.registerClient(ui);
        ui.start();
    }
}