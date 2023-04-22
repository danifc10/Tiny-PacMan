package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import pt.isec.pa.tinypac.GameController;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

import java.io.IOException;

public class GameUI {
    IGameEngine gameEngine;
    GameController game;
    Terminal terminal;
    GameContext fsm;
    MazeDisplay display;
    MazeControl mazeControl;

    public GameUI(MazeControl mazeControl, GameContext context, GameController controller, IGameEngine gameEngine) throws IOException {
        this.mazeControl = mazeControl;
        this.fsm = context;
        this.game = controller;
        TerminalSize size = new TerminalSize(80, 60);
        TerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorTitle("TinyPAcMan").setInitialTerminalSize(size);
        terminal = terminalFactory.createTerminal();
        this.gameEngine = gameEngine;
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
                case PAUSE -> pauseUI();
            }
        }
    }

    void initialUI() throws IOException {
        // inicia o terminal
        terminal.clearScreen();
        System.out.println("enter initial state");
        terminal.setCursorVisible(false);
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        terminal.setCursorPosition(30, 5);
        terminal.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        terminal.putString("TinyPAcMan");
        terminal.setCursorPosition(25, 7);
        terminal.setForegroundColor(TextColor.ANSI.WHITE);
        terminal.putString("PRESS ONE KEY TO START");

        // mostra labirinto
        display = new MazeDisplay(fsm.getMaze(), terminal);
        display.paint();

        // gameengine
        gameEngine.registerClient(display);
        gameEngine.registerClient(this.game);

        // verifica a primeira transição
        KeyStroke key = terminal.readInput();
        int direction = readDirection(key);
        fsm.startGame(direction);

        terminal.clearScreen();
    }

    void movementUI() {
        System.out.println("enter movement state");
        // espera 5 segundos e liberta os fantasmas
        fsm.setGhostsFree();
        System.out.println("leaving movement state");
    }

    void playingUI() throws IOException {
        // atualiza o loop de jogo verifica se comeu bola com poderes ou se foi comido
        game.update(terminal);
        // mostra pontos e outro info
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
    }

    private void vulnerableUI() throws IOException {
        System.out.println("enter vulnerable state");
        // faz o loop do jogo em modo vulnerable durante x segundos
        game.update(terminal);
        System.out.println("leaving vulnerable state");
        fsm.endVulnerableTime();
    }

    void finalUI() throws IOException {
        finish = true;
        terminal.clearScreen();
        terminal.putString("FIM");
        terminal.close();

    }

    void gameOverUI()  {
        System.out.println("aqui");
        fsm.restart();
    }

    void winUI(){
        if(fsm.getLevel() == 20)
            fsm.endGame();
        else
            fsm.levelUp();
    }

    void pauseUI() throws IOException {
        KeyStroke key = terminal.readInput();
        if(key.getKeyType() == KeyType.Character)
            fsm.resumeGame();
    }

}