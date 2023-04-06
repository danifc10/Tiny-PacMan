package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.elements.*;
import pt.isec.pa.tinypac.ui.text.GameUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*Maze maze = new Maze(31, 29, "Level101.txt");
        MazeDisplay display;
        try {
            display = new MazeDisplay(maze);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        display.paint(maze);

        */
         final int WIDTH = 800; // largura da janela
        final int HEIGHT = 600; // altura da janela
         final int TILE_SIZE = 20; // tamanho do tile
          final int ROWS = HEIGHT / TILE_SIZE; // número de linhas
          final int COLS = WIDTH / TILE_SIZE; // número de colunas

          final int LEFT = 0;
          final int RIGHT = 1;
          final int UP = 2;
          final int DOWN = 3;

        Maze maze = new Maze(31, 29, "Level101.txt");
        PacMan pacMan = new PacMan(maze.getXstart(),maze.getYstart(), 1,1);
        Ghost[] ghosts = {
                new Blinky(0, 0, RIGHT,1),
                new Pinky(WIDTH - TILE_SIZE, 0, UP,1),
                new Inky(0, HEIGHT - TILE_SIZE, DOWN, 1),
                new Clyde(WIDTH - TILE_SIZE, HEIGHT - TILE_SIZE, LEFT, 1)
        };

        GameContext fsm = new GameContext(pacMan, ghosts, maze);
        GameUI ui = new GameUI(fsm);
        ui.start();
    }
}