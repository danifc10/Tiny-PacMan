package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class PacManStart implements IMazeElement {
    public static final char symbol = 'M';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
