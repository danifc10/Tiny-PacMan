package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class Point implements IMazeElement , Serializable {
    public static final char symbol = 'o';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
