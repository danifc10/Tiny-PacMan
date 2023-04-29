package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

public class Fruit implements IMazeElement {
    public static final char symbol= 'F';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
