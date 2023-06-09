package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class GhostSpot implements IMazeElement, Serializable {
    public static final char symbol= 'y';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
