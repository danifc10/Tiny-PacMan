package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing a warp
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class WarpZone implements IMazeElement, Serializable {
    /**
     * Reference to warp symbol
     */
    public static final char symbol = 'W';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

