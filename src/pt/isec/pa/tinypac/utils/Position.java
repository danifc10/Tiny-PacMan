package pt.isec.pa.tinypac.utils;

import java.io.Serializable;
/**
 * class to make a position
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Position implements Serializable {
    private int x;
    private int y;

    /**
     * Default constructor
     * @param (x,y) coords for position
     *
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get row
     * @return x row
     *
     */
    public int getX() {
        return x;
    }
    /**
     * Set a new row
     * @param  x new row
     *
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Get collum
     * @return y collum
     *
     */
    public int getY() {
        return y;
    }

    /**
     * Set a new collum
     * @param  y new collum
     *
     */
    public void setY(int y) {
        this.y = y;
    }
}
