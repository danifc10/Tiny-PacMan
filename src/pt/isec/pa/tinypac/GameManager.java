package pt.isec.pa.tinypac;

import javafx.application.Platform;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager implements IGameEngineEvolve {
    public static final String file = "files\\game.dat";
    private GameContext fsm;
    PropertyChangeSupport pcs;
    private boolean activateTimer = false;

    public GameManager() {
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

    public void end() {
        //fsm.endGame();
        pcs.firePropertyChange(null,null,null);
    }

    // transitions
    public void changePacManDirection(int direction){
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

    public void registerPoints() {
    }
}

