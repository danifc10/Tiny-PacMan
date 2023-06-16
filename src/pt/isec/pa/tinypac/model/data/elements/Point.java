package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing a dot
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Point implements IMazeElement , Serializable {
    /**
     * Reference to point symbol
     */
    public static final char symbol = 'o';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
