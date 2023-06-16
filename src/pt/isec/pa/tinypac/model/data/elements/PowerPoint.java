package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing a power dot
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class PowerPoint implements IMazeElement , Serializable {
    /**
     * Reference to power point symbol
     */
    public static final char symbol = 'O';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
