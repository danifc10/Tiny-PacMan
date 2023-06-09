package pt.isec.pa.tinypac.model;

import java.io.Serializable;

public class Score implements Serializable {
    private String name;
    private int points;

    public void Score(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
