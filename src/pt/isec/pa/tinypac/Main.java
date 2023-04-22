package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.Position;
import pt.isec.pa.tinypac.model.data.elements.Blinky;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.elements.Pinky;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.text.GameUI;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        final int LEFT = 2;
        final int RIGHT = 1;
        final int UP = 3;
        final int DOWN = 4;
/*
        Map <Integer, String> levels = new HashMap<>();
        levels.put(1,"Level101.txt");
        levels.put(2,"Level102.txt");
*/
        MazeControl mazeControl = new MazeControl("Level101.txt");
        List<Position> positions = mazeControl.getGhostStartPositions();
        PacMan pacMan = new PacMan(mazeControl.getXstart(), mazeControl.getYstart(), 1,1, mazeControl);
        Ghost[] ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), Direction.RIGHT, 1, mazeControl),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), Direction.RIGHT, 1, mazeControl)
        };

        IGameEngine gameEngine = new GameEngine();
        GameContext fsm = new GameContext(pacMan, ghosts, mazeControl, gameEngine);

        GameController controller = new GameController(mazeControl,fsm, pacMan, ghosts);
        GameUI ui = new GameUI(mazeControl, fsm, controller, gameEngine);

        ui.start();
    }
}