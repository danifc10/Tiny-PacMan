package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class GhostGate implements IMazeElement {
    public static final char symbol= 'Y';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

