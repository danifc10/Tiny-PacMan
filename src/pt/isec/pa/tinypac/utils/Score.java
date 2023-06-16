package pt.isec.pa.tinypac.utils;

import java.io.Serializable;
/**
 * class representing a top 5 score
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Score implements Serializable, Comparable<Score>{
    private String name;
    private int points;
    /**
     * Default constructor
     * @param (name, points) data for the score
     *
     */
    public Score(String name, int points){
        this.name = name;
        this.points = points;
    }

    /**
     * Get score name
     * @return name player name
     */
    public String getName() {
        return name;
    }
    /**
     * Get points
     * @return points player points
     */
    public int getPoints() {
        return points;
    }

    /**
     * to sort from largest to smallest
     *
     */
    @Override
    public int compareTo(Score other) {
        // Comparação invertida para ordenar do maior para o menor
        return Integer.compare(other.points, this.points);
    }
}
