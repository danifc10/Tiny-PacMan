package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing the ghost jail
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GhostSpot implements IMazeElement, Serializable {
    /**
     * Reference to ghost spot symbol
     */
    public static final char symbol= 'y';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
