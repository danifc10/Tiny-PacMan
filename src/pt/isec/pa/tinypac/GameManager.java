package pt.isec.pa.tinypac;

import javafx.application.Platform;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.Score;
import pt.isec.pa.tinypac.model.Top5;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;
/**
 * class where all the data as connected and manage
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GameManager implements IGameEngineEvolve {
    public static final String file = "files\\game.dat";
    public static final String TOP5_FILE = "files\\scores.bin";
    private Top5 top5;
    private GameContext fsm;
    PropertyChangeSupport pcs;
    private boolean activateTimer = false;

    public GameManager() {
        loadFromFile(TOP5_FILE);
        fsm = new GameContext();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void start() {
        fsm.startGame();
        pcs.firePropertyChange(null,null,null);
    }

    public void loadFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Top 5 loaded successfully.");
            this.top5 = (Top5) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Top 5: " + e.getMessage());
            this.top5 = new Top5();
        }
    }

    public void saveToFile(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(top5);
            System.out.println("Top 5 saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving Top 5: " + e.getMessage());
        }
    }

    public void end() {
        //fsm.endGame();
        pcs.firePropertyChange(null,null,null);
    }

    // transitions
    public void changePacManDirection(Direction direction){
        activateTimer = true;
        fsm.setPacManNewDirection(direction);
    }

    public void pause() {
        fsm.pauseGame();
    }

    public void resume() {
        fsm.resumeGame();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

        (new Thread(() -> {
            Platform.runLater(() -> pcs.firePropertyChange(null,null,null));
        })).start();

        fsm.evolve();

        if(activateTimer)
            fsm.timer();
    }

    // data
    public char[][] getMaze(){
        return fsm.getMaze();
    }

    public GameStates getState() {
        return fsm.getState();
    }

    public int getPoints() {
        return fsm.getPoints();
    }

    public int getLife() {
        return fsm.getPacManLife();
    }

    public int getLevel(){
        return fsm.getLevel();
    }

    public int getTime(){
        return fsm.getTime();
    }

    public void saveGame(){
        fsm.save();
    }

    public void loadGame(){
        fsm.load();
    }

    public void registerPoints(String name) {
        top5.addScore(name, getPoints());
        saveToFile(TOP5_FILE);
    }

    public List<Score> getTop5(){
        return top5.getScores();
    }

    public boolean canReachTop5() {
        return top5.canAddScore(getPoints());
    }

    public void restart() {
        fsm = new GameContext();
        top5 = new Top5();
        fsm.startGame();
    }
}

