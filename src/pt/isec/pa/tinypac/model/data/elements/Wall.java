package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing a wall
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Wall implements IMazeElement , Serializable {
    /**
     * Reference to wall symbol
     */
    public static final char symbol = 'x';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
