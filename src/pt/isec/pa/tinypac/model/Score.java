package pt.isec.pa.tinypac.model;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score>{
    private String name;
    private int points;

    public Score(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }
    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Score other) {
        // Comparação invertida para ordenar do maior para o menor
        return Integer.compare(other.points, this.points);
    }
}
