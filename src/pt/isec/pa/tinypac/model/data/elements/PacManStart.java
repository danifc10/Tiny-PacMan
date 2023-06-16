package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing the PacMan start
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class PacManStart implements IMazeElement, Serializable {
    /**
     * Reference to PacMan start symbol
     */
    public static final char symbol = 'M';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
