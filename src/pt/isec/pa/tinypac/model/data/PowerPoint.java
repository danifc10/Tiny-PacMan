package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class PowerPoint implements IMazeElement {
    public static final char symbol = 'O';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
