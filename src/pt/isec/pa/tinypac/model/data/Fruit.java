package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;

public class Fruit implements IMazeElement , Serializable {
    public static final char symbol= 'F';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
