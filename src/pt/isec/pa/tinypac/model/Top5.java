package pt.isec.pa.tinypac.model;

import java.io.*;
import java.util.ArrayList;
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
        if (scores.size() > MAX_SCORES) {
            scores.remove(MAX_SCORES);
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    public void saveToFile(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("Top 5 saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving Top 5: " + e.getMessage());
        }
    }

    public Top5 loadFromFile(String fileName) {
        Top5 top5 = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Top 5 loaded successfully.");
            return top5 = (Top5) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Top 5: " + e.getMessage());
            return top5 = new Top5();
        }
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