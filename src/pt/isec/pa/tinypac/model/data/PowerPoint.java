package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class PowerPoint implements IMazeElement , Serializable {
    public static final char symbol = 'O';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
