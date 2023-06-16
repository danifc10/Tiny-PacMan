package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;
/**
 * context for the fsm
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GameContext {
    /**
     * Reference to the game name file
     */
    private static final String file = "files\\game.bin";
    private GameData gameData;
    private IGameStates currentState;
    private IGameEngine gameEngine;
    /**
     * Default constructor
     *
     */
    public GameContext(){
        this.gameEngine =  new GameEngine();
        load();
        this.currentState = new InitialState(this,gameData, gameEngine);
    }

    /**
     * Get current State
     * @return currentState
     */
    public GameStates getState(){
        return currentState.getState();
    }

    void changeState(IGameStates newState){
        this.currentState = newState;
    }

    // tranciçoes

    /**
     * Change PacMan direction
     * @param readDirection new PacMan direction
     */
    public void setPacManNewDirection(Direction readDirection) {
        currentState.changeDirection(readDirection);
    }

    /**
     * Pause game function
     *
     */
    public boolean pauseGame(){return currentState.pauseGame();}

    // dados

    /**
     * Get points of the game
     * @return gameData.getPoints() - number of points
     */
    public int getPoints(){
        return gameData.getPoints();
    }
    /**
     * Get game level
     * @return gameData.getLevel() game level
     */
    public int getLevel(){
        return gameData.getLevel();
    }
    /**
     * Return the actual maze
     * @return gameData.getMaze() - actual maze
     */
    public char[][] getMaze(){ return gameData.getMaze();}
    /**
     * Get PacMan life
     * @return gameData.getPacManLife() - number of lifes
     */
    public int getPacManLife() {
        return gameData.getPacManLife();
    }

    /**
     * Function to call the evolve of the current state
     *
     */
    public void evolve() {
        currentState.evolve();
    }

    /**
     * Count game time
     *
     */
    public void timer() {
        gameData.timer();
    }

    /**
     * Get time
     * @return gameData.getTime() - game time
     */
    public int getTime() {
        return gameData.getTime();
    }

    /**
     * Save game function
     * @return true if game have been saved false if not
     */
    public boolean save() {
        try (FileOutputStream fs = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fs)) {
            oos.writeObject(gameData);
            System.out.println("Jogo gravado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao guardar jogo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load game function
     *
     */
    public void load() {
        File file2 = new File(file);
        if (file2.exists()) {
            try (FileInputStream fs = new FileInputStream(file2);
                 ObjectInputStream ois = new ObjectInputStream(fs)) {
                this.gameData = (GameData) ois.readObject();
                System.out.println("Jogo restaurado com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro ao abrir ficheiro: " + e.getMessage());
                this.gameData = new GameData();
            } finally {
                file2.delete();
            }
        } else {
            System.out.println("Ficheiro de jogo não encontrado. Um novo jogo será criado.");
            this.gameData = new GameData();
        }
    }

    /**
     * Get PacMan current direction
     * @return gameData.getPacManDirection() - current PacMan direction
     */
    public Direction getPacManDirection() {
        return gameData.getPacManDirection();
    }

    /**
     * Get number of ghosts
     * @return gameData.getNumOfGhosts() - number of ghosts
     */
    public int getNGhosts() {
        return gameData.getNumOfGhosts();
    }
}
