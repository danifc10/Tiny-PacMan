package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class Point implements IMazeElement {
    public static final char symbol = 'o';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
