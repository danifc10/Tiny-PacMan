package pt.isec.pa.tinypac;

import javafx.application.Platform;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.model.fsm.GameStates;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameManager implements IGameEngineEvolve {
    private GameContext fsm;
    private int time = 0;
    PropertyChangeSupport pcs;

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

    public char[][] getMaze(){
        return fsm.getMaze();
    }

    public GameStates getState() {
        return fsm.getState();
    }

    // transitions and data here
    public void changePacManDirection(int direction){
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
        time++;
        (new Thread(() -> {
            Platform.runLater(() -> pcs.firePropertyChange(null,null,null));
        })).start();

        fsm.evolve();
    }

    public int getPoints() {
        return fsm.getPoints();
    }

    public int getLife() {
        return fsm.getPacManLife();
    }

    public int getTime(){
        return time;
    }

    public void saveGame() {
        // implementar saves
    }
}

