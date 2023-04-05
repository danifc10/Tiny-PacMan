package pt.isec.pa.tinypac.ui.gui;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalFactory;
import pt.isec.pa.tinypac.model.data.Maze;

import java.io.IOException;

public class MazeDisplay {
    private Maze maze;
    private Terminal terminal;

    public MazeDisplay(Maze maze) throws IOException {
        this.maze = maze;
        TerminalSize size = new TerminalSize(80,60);
        TerminalFactory terminalFactory = new DefaultTerminalFactory().setTerminalEmulatorTitle("MAZE").setInitialTerminalSize(size);
        terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
        terminal.setCursorPosition(30, 5);
        terminal.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        terminal.putString("TinyPAcMan");
    }

    public void paint(Maze maze) throws IOException {
        TextGraphics tg = terminal.newTextGraphics();
        char[][] char_board = maze.getMaze();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = char_board[y][x];
                switch (c) {
                    case 'x' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLUE);
                        tg.fillRectangle(new TerminalPosition(20 + x,10 + y), new TerminalSize(1,1),' ');
                    }
                    case 'o' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.YELLOW);
                        tg.putString(x + 20,y + 10,".");
                    }
                    case 'F' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
                        tg.putString(x +20,y + 10,"O");
                    }
                    case 'O' -> {
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                        tg.putString(x +20,y + 10,"o");
                    }
                    case 'Y' -> {
                        tg.setBackgroundColor(TextColor.ANSI.RED);
                        tg.fillRectangle(new TerminalPosition(20 + x,10 + y), new TerminalSize(1,1),' ');
                    }
                    case 'y' -> {
                        tg.setBackgroundColor(TextColor.ANSI.CYAN);
                        tg.fillRectangle(new TerminalPosition(20 + x,10 + y), new TerminalSize(1,1),' ');
                    }
                    case 'W' -> {
                        tg.setBackgroundColor(TextColor.ANSI.WHITE);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10+ y), new TerminalSize(1,1),' ');
                    }
                    case 'M' -> {
                        tg.setBackgroundColor(TextColor.ANSI.GREEN);
                        tg.fillRectangle(new TerminalPosition(20 + x, 10 + y), new TerminalSize(1,1), ' ');
                    }

                }

            }
        }
    }
/*
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK); // define a cor de fundo para preto
        g.fillRect(0, 0, getWidth(), getHeight());
        char[][] char_board = maze.getMaze();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = char_board[y][x];
                    switch (c) {
                        case 'x' -> {
                            g.setColor(Color.BLUE);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                        case 'o' -> {
                            g.setColor(Color.yellow);
                            g.fillOval(x * 20 + 6, y * 20 + 6, 5, 5);
                        }
                        case 'F' -> {
                            g.setColor(Color.RED);
                            g.fillOval(x * 20 + 5, y * 20 + 5, 10, 10);
                        }
                        case 'O' -> {
                            g.setColor(Color.GREEN);
                            g.fillOval(x * 20 + 5, y * 20 + 5, 10, 10);
                        }
                        case 'Y' -> {
                            g.setColor(Color.GRAY);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                        case 'y' -> {
                            g.setColor(Color.ORANGE);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                        case 'W' -> {
                            g.setColor(Color.white);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                        case 'M' -> {
                            g.setColor(Color.PINK);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                        default -> {
                            g.setColor(Color.BLACK);
                            g.fillRect(x * 20, y * 20, 20, 20);
                        }
                    }

                g.setColor(Color.BLACK);
                g.drawRect(x * 20, y * 20, 20, 20);
            }
        }
    }
*/



}