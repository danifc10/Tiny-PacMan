package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;

import java.io.Serializable;
/**
 * class representing the ghost gate
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GhostGate implements IMazeElement , Serializable {
    /**
     * Reference to Ghost gate symbol
     */
    public static final char symbol= 'Y';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

