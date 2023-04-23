package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.text.GameUI;

import java.io.IOException;

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

        IGameEngine gameEngine = new GameEngine();

        GameContext fsm = new GameContext(mazeControl, gameEngine);
        GameUI ui = new GameUI(mazeControl, fsm);
        gameEngine.registerClient(ui);
        ui.start();
    }
}