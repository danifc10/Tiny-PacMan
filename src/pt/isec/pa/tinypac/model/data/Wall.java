package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class Wall implements IMazeElement , Serializable {
    public static final char symbol = 'x';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
