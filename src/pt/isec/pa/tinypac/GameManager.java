package pt.isec.pa.tinypac;

import javafx.application.Platform;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.utils.Score;
import pt.isec.pa.tinypac.utils.Top5;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;
/**
 * class that manage the all game
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GameManager implements IGameEngineEvolve {
    /**
     * Reference to the top5 name file
     */
    public static final String TOP5_FILE = "files\\scores.bin";
    private Top5 top5;
    private GameContext fsm;
    PropertyChangeSupport pcs;
    private boolean activateTimer = false;

    /**
     * Default constructor
     *
     */
    public GameManager() {
        loadTop5(TOP5_FILE);
        fsm = new GameContext();
        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * first function called
     *
     */
    public void start() {
        pcs.firePropertyChange(null,null,null);
    }

    /**
     * function to load top5 from file
     * @param fileName top5 file name
     */
    public void loadTop5(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Top 5 loaded successfully.");
            this.top5 = (Top5) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading Top 5: " + e.getMessage());
            this.top5 = new Top5();
        }
    }

    /**
     * function to save top5
     * @param fileName top5 file name
     */
    public void saveTop5(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(top5);
            System.out.println("Top 5 saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving Top 5: " + e.getMessage());
        }
    }

    /**
     * Change PacMan direction
     * @param direction new PacMan direction
     */
    public void changePacManDirection(Direction direction){
        activateTimer = true;
        fsm.setPacManNewDirection(direction);
    }

    /**
     * Pause game
     *
     */
    public void pause() {
        fsm.pauseGame();
    }


    /**
     * Calls function evolve of the current state and count the game time
     * @param (gameEngine, currentTime) needs
     */
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

        (new Thread(() -> {
            Platform.runLater(() -> pcs.firePropertyChange(null,null,null));
        })).start();

        fsm.evolve();

        if(activateTimer)
            fsm.timer();
    }

    /**
     * Return the actual maze
     * @return fsm.getMaze() - actual maze
     */
    public char[][] getMaze(){
        return fsm.getMaze();
    }

    /**
     * Get the current state
     * @return fsm.getState - currentState
     */
    public GameStates getState() {
        return fsm.getState();
    }

    /**
     * Get points of the game
     * @return fsm.getPoints() - number of points
     */
    public int getPoints() {
        return fsm.getPoints();
    }

    /**
     * Get PacMan life
     * @return fsm.getPacManLife() - number of lifes
     */
    public int getLife() {
        return fsm.getPacManLife();
    }

    /**
     * Get game level
     * @return fsm.getLevel() game level
     */
    public int getLevel(){
        return fsm.getLevel();
    }
    /**
     * Get time
     * @return fsm.getTime() - time
     */
    public int getTime(){
        return fsm.getTime();
    }

    /**
     * Add score to top5
     * @param name player name
     */
    public void registerPoints(String name) {
        top5.addScore(name, getPoints());
        saveTop5(TOP5_FILE);
    }

    /**
     * Get list for top5
     * @return top5.getScores() - array list with all registed scores
     */
    public List<Score> getTop5(){
        return top5.getScores();
    }

    /**
     * See if score can reach top5 or not
     * @return boolean - true if can add score and false if can't
     */
    public boolean canReachTop5() {
        return top5.canAddScore(getPoints());
    }

    /**
     * Restart game
     *
     */
    public void restart() {
        fsm = new GameContext();
        top5 = new Top5();
    }

    /**
     * Get PacMan direction
     * @return fsm.getPacManDirection() - current PacMan direction
     */
    public Direction getPacManDirection() {
        return fsm.getPacManDirection();
    }

    /**
     * Save game
     * @return true if game have been saved false if not
     */
    public boolean save(){
        return fsm.save();
    }

    /**
     * Get number of alive ghosts
     * @return fsm.getNGhosts() - number of ghosts alive
     */
    public int getNumGhosts(){
        return fsm.getNGhosts();
    }

}

