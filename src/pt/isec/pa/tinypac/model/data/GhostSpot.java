package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class GhostSpot implements IMazeElement {
    public static final char symbol= 'y';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
