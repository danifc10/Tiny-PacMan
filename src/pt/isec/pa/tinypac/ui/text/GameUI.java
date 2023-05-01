package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.GameController;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.io.IOException;

public class GameUI implements IGameEngineEvolve {
    private GameController game;
    private IGameEngine gameEngine;
    private Terminal terminal;
    private GameContext fsm;

    public GameUI(GameContext context, GameController game, IGameEngine gameEngine, Terminal terminal) throws IOException {
        this.fsm = context;
        this.game = game;
        this.gameEngine = gameEngine;
        this.terminal = terminal;
    }

    boolean finish = false;

    public void start() throws IOException {
        while(!finish){
            switch (fsm.getState()){
                case INITIAL -> initialUI();
                case PLAYING -> playingUI();
                case MOVEMENT -> playingUI();
                case GAME_OVER -> gameOverUI();
                case WIN -> winUI();
                case VULNERABLE -> playingUI();
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
        game.setPacManNewDirection(readDirection(key));
        gameEngine.registerClient(game);
        gameEngine.start(180);
        fsm.startGame();
        terminal.clearScreen();
    }

    void playingUI() throws IOException {
        KeyStroke key = terminal.readInput();
        if(key.getKeyType() == KeyType.Character) {
            gameEngine.unregisterClient(game);
            fsm.pauseGame();
        }
        game.setPacManNewDirection(readDirection(key));
    }

    void gameOverUI() throws IOException {
        System.out.println("game over");
        gameEngine.unregisterClient(game);
        if(game.getPacManLife() - 1> 0) {
            game.restartMaze();
            fsm.restart();
        }else{
            finish = true;
            terminal.clearScreen();
            terminal.flush();
            terminal.setCursorPosition(23, 7);
            terminal.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            terminal.putString(" Game Over - press any key to close ");
            terminal.readInput();
            terminal.close();
            gameEngine.stop();
        }
    }

    void winUI(){
        System.out.println("vitoria");
        gameEngine.unregisterClient(game);
        //verificar se nivel = 20 entao salta
        game.levelUp();
        fsm.levelUp();
    }

    void pauseUI() throws IOException {
        // dar opcao de save do jogo
        KeyStroke key = terminal.readInput();
        if(key.getKeyType() == KeyType.Character) {
            gameEngine.registerClient(game);
            fsm.resumeGame();
        }
    }

    private void show() throws IOException {
        TextGraphics tg = terminal.newTextGraphics();
        char[][] env = game.getMaze().getMazeControl();

        // mostra pontos e outro info
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
        terminal.setCursorPosition(20, 46);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("LEVEL:" + fsm.getLevel());
        terminal.setCursorPosition(30, 46);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("LIFE:" + game.getPacManLife());
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
                        if (fsm.getState() == GameStates.VULNERABLE) {
                            tg.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                        else {
                            tg.setBackgroundColor(TextColor.ANSI.MAGENTA);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                    }
                    case 'K' -> {
                        if (fsm.getState() == GameStates.VULNERABLE){
                            tg.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                        else {
                            tg.setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                    }
                    case 'I' -> {
                        if (fsm.getState() == GameStates.VULNERABLE){
                            tg.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                        else {
                            tg.setBackgroundColor(TextColor.ANSI.YELLOW);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                    }
                    case 'C' -> {
                        if(fsm.getState() == GameStates.VULNERABLE){
                            tg.setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }else{
                            tg.setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
                            tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1, 1), ' ');
                        }
                    }
                }
            }
        }
        terminal.flush();
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

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}