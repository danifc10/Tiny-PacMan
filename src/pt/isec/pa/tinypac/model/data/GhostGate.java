package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class GhostGate implements IMazeElement , Serializable {
    public static final char symbol= 'Y';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

