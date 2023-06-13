package pt.isec.pa.tinypac.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Top5 implements Serializable {

    private static final int MAX_SCORES = 5;
    private List<Score> scores;

    public Top5() {
        scores = new ArrayList<>();
    }

    public void addScore(String name, int score) {
        Score newScore = new Score(name, score);
        int minScore = Integer.MAX_VALUE;
        if (scores.size() == MAX_SCORES){
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i).getPoints() < minScore) {
                    minScore = scores.get(i).getPoints();
                }
            }
            if(minScore < newScore.getPoints()) {
                for (int i = 0; i < scores.size(); i++) {
                    if (minScore == scores.get(i).getPoints()) {
                        scores.remove(i);
                        scores.add(newScore);
                        minScore = Integer.MAX_VALUE;
                    }
                }
            }
        }else{
            scores.add(newScore);
        }
        Collections.sort(scores);
        if (scores.size() > MAX_SCORES) {
            scores.remove(MAX_SCORES);
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    public boolean canAddScore(int points) {
        int minScore = Integer.MAX_VALUE;
        if (scores.size() == MAX_SCORES) {
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i).getPoints() < minScore) {
                    minScore = scores.get(i).getPoints();
                }
            }

            if(points > minScore){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}