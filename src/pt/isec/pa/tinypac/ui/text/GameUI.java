package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import pt.isec.pa.tinypac.GameController;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameContext;


import java.io.IOException;

public class GameUI implements IGameEngineEvolve {
    Screen screen;
    IGameEngine gameEngine;
    GameController game;
    Terminal terminal;
    GameContext fsm;
    MazeControl mazeControl;

    public GameUI(MazeControl mazeControl, GameContext context) throws IOException {
        this.mazeControl = mazeControl;
        this.fsm = context;

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

        show();

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
        //game.update(terminal);
        KeyStroke key = terminal.readInput();
        // verificar direção
        fsm.setPacManNewDirection(readDirection(key));
        //fsm.checks();
    }

    private void vulnerableUI() throws IOException {
        System.out.println("enter vulnerable state");
        // faz o loop do jogo em modo vulnerable durante x segundos
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

    private void show() throws IOException {
        TextGraphics tg = terminal.newTextGraphics();
        char[][] env = mazeControl.getMazeControl();
        // mostra pontos e outro info
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                char c = env[y][x];
                switch (c) {
                    case 'x' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLUE);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'o' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.YELLOW);
                        tg.putString(x + 20, y + 10, ".");
                    }
                    case 'F' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                        tg.putString(x + 20, y + 10, "O");
                    }
                    case 'O' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                        tg.putString(x + 20, y + 10, "o");
                    }
                    case 'Y' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'y' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'W' -> {
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'M' -> {
                        tg.setBackgroundColor(TextColor.ANSI.GREEN);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'P' -> {
                        tg.setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case ' ' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'B' -> {
                        tg.setBackgroundColor(TextColor.ANSI.MAGENTA);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'K' -> {
                        tg.setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                    case 'I' -> {
                        tg.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                    }
                }
            }
        }
        terminal.flush();
    }


    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}