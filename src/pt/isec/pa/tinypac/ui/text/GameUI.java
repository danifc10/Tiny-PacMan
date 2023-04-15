package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import pt.isec.pa.tinypac.GameController;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

import java.io.IOException;

public class GameUI {
    GameController game;
    Terminal terminal;
    GameContext fsm;
    MazeDisplay display;
    MazeControl mazeControl;

    public GameUI(MazeControl mazeControl, GameContext context, GameController controller) throws IOException {
        this.mazeControl = mazeControl;
        this.fsm = context;
        this.game = controller;
        TerminalSize size = new TerminalSize(80, 60);
        TerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorTitle("TinyPAcMan").setInitialTerminalSize(size);
        terminal = terminalFactory.createTerminal();
    }

    public int readDirection(KeyStroke key){
        switch (key.getKeyType()) {
            case ArrowLeft -> {
                return 2;
            }
            case ArrowRight -> {
                return 1;
            }
            case ArrowUp -> {
                return 3;
            }
            case ArrowDown -> {
                return 4;
            }
        }
        return 0;
    }

    boolean finish = false;
    public void start() throws IOException {
        while(!finish){
            switch (fsm.getState()){
                case INITIAL -> initialUI();
                case PLAYING -> playingUI();
                case MOVEMENT -> movementUI();
                case FINAL -> finalUI();
                case GAME_OVER -> gameOverUI();
                case WIN -> winUI();
                case VULNERABLE -> vulnerableUI();
            }
        }
    }

    private void vulnerableUI() throws IOException {
        System.out.println("enter vulnerable state");
        for(int i = 0 ; i < 10 ; i++){
            game.update(terminal);
            paint();
            terminal.flush();
        }
        System.out.println("leaving vulnerable state");
        fsm.endVulnerableTime();
    }

    void finalUI() throws IOException {
        finish = true;
        terminal.clearScreen();
        terminal.putString("FIM");
        terminal.close();

    }

    void movementUI() {
        System.out.println("enter movement state");
        fsm.setGhostsFree();
        System.out.println("leaving movement state");
    }

    void initialUI() throws IOException {
        // inicia o terminal
        System.out.println("enter initial state");
        terminal.setCursorVisible(false);
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        terminal.setCursorPosition(30, 5);
        terminal.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        terminal.putString("TinyPAcMan");
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
        terminal.setCursorPosition(20, 46);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("TIME:" + fsm.getTime());
        display = new MazeDisplay(fsm.getMaze(), terminal);
        paint();
        KeyStroke key = terminal.readInput();
        int direction = readDirection(key);
        game.movePacMan(direction);
        paint();
        terminal.flush();
        fsm.startGame();
        System.out.println("leaving initial state");
    }

    void playingUI() throws IOException {
        System.out.println("enter playing state");
        //KeyStroke key = terminal.readInput();
        paint();
        game.update(terminal);
        paint();
        System.out.println("leaving playing state");
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
        terminal.setCursorPosition(20, 46);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("TIME:" + fsm.getTime());
        terminal.flush();
    }

    void gameOverUI()  {
        if(game.getLivesPacMan() > 0)
            fsm.restart();
        else
            fsm.endGame();
    }

    void winUI(){
        if(fsm.getLevel() == 20)
            fsm.endGame();
        else
            fsm.levelUp();
    }

    public void paint() throws IOException {
        display.paint(mazeControl);
    }

}