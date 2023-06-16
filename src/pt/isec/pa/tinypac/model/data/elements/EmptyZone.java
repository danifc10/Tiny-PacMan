package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing an empty zone
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class EmptyZone implements IMazeElement , Serializable {
    /**
     * Reference to Empty zone symbol
     */
    public static final char symbol = ' ';

    @Override
    public char getSymbol() {
        return symbol;
    }
}