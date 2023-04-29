package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class WrapZone implements IMazeElement {
    public static final char symbol = 'W';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

