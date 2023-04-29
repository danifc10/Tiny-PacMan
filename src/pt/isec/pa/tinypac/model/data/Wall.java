package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class Wall implements IMazeElement {
    public static final char symbol = 'x';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
