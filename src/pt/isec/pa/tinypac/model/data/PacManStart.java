package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class PacManStart implements IMazeElement, Serializable {
    public static final char symbol = 'M';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
