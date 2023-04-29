package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class EmptyZone implements IMazeElement {
    public static final char symbol = ' ';

    @Override
    public char getSymbol() {
        return symbol;
    }
}