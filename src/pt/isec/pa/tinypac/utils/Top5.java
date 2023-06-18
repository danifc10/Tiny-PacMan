package pt.isec.pa.tinypac.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * class representing the top5
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Top5 implements Serializable {

    private static final int MAX_SCORES = 5;
    private List<Score> scores;

    /**
     * Default constructor
     *
     */
    public Top5() {
        scores = new ArrayList<>();
    }

    /**
     * Add a new score
     * @param (name, score) player name and points
     */
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

    /**
     * Get all the scores
     * @return scores array list with all scores
     */
    public List<Score> getScores() {
        return scores;
    }

    /**
     * If can add the score or not
     * @param (points) player points
     * @return true if can add and false if can't
     */
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