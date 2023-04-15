package pt.isec.pa.tinypac.ui.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.data.MazeControl;

import java.io.IOException;

public class MazeDisplay {
    private MazeControl maze;
    private Terminal terminal;
    TextGraphics tg;

    public MazeDisplay(MazeControl maze, Terminal terminal) throws IOException {
        this.maze = maze;
        this.terminal = terminal;
        tg = terminal.newTextGraphics();
    }

    public void paint(MazeControl maze) throws IOException {
        TextGraphics tg = terminal.newTextGraphics();
        char[][] char_board = maze.getMazeControl();
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
                    case 'P'->{
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
                        tg.putString(x +20,y + 10,"P");
                    }
                    case ' '->{
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.fillRectangle(new TerminalPosition(20 + x,10 + y), new TerminalSize(1,1),' ');
                    }
                    case 'G'->{
                        tg.setBackgroundColor(TextColor.ANSI.BLACK);
                        tg.setForegroundColor(TextColor.ANSI.RED);
                        tg.putString(x +20,y + 10,"G");
                    }

                }

            }
        }
    }
}