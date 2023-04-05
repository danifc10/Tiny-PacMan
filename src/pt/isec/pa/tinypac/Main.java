package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Maze maze = new Maze(31, 29, "Level101.txt");
        MazeDisplay display;
        try {
            display = new MazeDisplay(maze);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        display.paint(maze);
    }
}