package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing a fruit
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Fruit implements IMazeElement , Serializable {
    /**
     * Reference to Fruit symbol
     */
    public static final char symbol= 'F';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
