package pt.isec.pa.tinypac;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
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

        Terminal terminal;
        TextGraphics tg;
        TerminalSize size = new TerminalSize(80,60);
        TerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorTitle("TinyPAcMan").setInitialTerminalSize(size);
        terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        terminal.setCursorPosition(30, 5);
        terminal.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        terminal.putString("TinyPAcMan");
        tg = terminal.newTextGraphics();

       // Maze maze = new Maze(31, 29, "Level101.txt");
        MazeControl mazeControl = new MazeControl("Level101.txt");
        PacMan pacMan = new PacMan(mazeControl.getXstart(), mazeControl.getYstart(), 1,1);
        Ghost[] ghosts = {
                new Blinky(mazeControl.getXghostStart(), mazeControl.getYghostStart(), UP,1),
        };

        GameContext fsm = new GameContext(pacMan, ghosts, mazeControl);
        GameUI ui = new GameUI(fsm, terminal);
        ui.start();

        /*IGameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(fsm);*/




    }
}