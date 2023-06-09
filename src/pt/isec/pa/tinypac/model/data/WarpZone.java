package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class WarpZone implements IMazeElement, Serializable {
    public static final char symbol = 'W';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

