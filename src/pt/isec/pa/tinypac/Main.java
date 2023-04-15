package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Blinky;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.text.GameUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final int LEFT = 2;
        final int RIGHT = 1;
        final int UP = 3;
        final int DOWN = 4;

        MazeControl mazeControl = new MazeControl("Level101.txt");
        PacMan pacMan = new PacMan(mazeControl.getXstart(), mazeControl.getYstart(), 1,1);
        Ghost[] ghosts = new Ghost[]{
                new Blinky(mazeControl.getXghostStart(), mazeControl.getYghostStart(), 3, 1),
        };
        GameContext fsm = new GameContext(pacMan, ghosts, mazeControl);
        GameController controller = new GameController(mazeControl,fsm, pacMan, ghosts);
        GameUI ui = new GameUI(mazeControl, fsm, controller);

        ui.start();

        /*IGameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(fsm);*/




    }
}