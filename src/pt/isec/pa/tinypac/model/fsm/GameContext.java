package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;
import pt.isec.pa.tinypac.utils.Direction;

import java.io.*;

public class GameContext {
    public static final String file = "files\\game.bin";
    private GameData gameData;
    private IGameStates currentState;
    private IGameEngine gameEngine;

    public GameContext(){
        this.gameEngine =  new GameEngine();
        load();
        this.currentState = new InitialState(this,gameData, gameEngine);
    }

    public GameStates getState(){
        return currentState.getState();
    }

    void changeState(IGameStates newState){
        this.currentState = newState;
    }

    // tranciçoes
    public boolean startGame(){
        gameData.initGame();
        return true;
    }
    public void setPacManNewDirection(Direction readDirection) {
        currentState.changeDirection(readDirection);
    }
    public boolean pauseGame(){return currentState.pauseGame();}
    public boolean resumeGame(){return currentState.resumeGame();}

    // dados
    public int getPoints(){
        return gameData.getPoints();
    }
    public void setPoints(int num){
        gameData.setPoints(num);
    }
    public int getLevel(){
        return gameData.getLevel();
    }
    public void setLevel(int i) {
        gameData.setLevel(i);
    }
    public char[][] getMaze(){ return gameData.getMaze();}

    public int getPacManLife() {
        return gameData.getPacManLife();
    }

    public void evolve() {
        currentState.evolve();
    }

    public void timer() {
        gameData.timer();
    }

    public int getTime() {
        return gameData.getTime();
    }

    public void save() {
        try(FileOutputStream fs = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fs)){
            oos.writeObject(gameData);
            oos.close();
            fs.close();
            System.out.println("Jogo gravado com sucesso.");
        }catch (IOException e){
            System.out.println("Erro ao guardar jogo");
        }
    }

    public void load(){
        File file2 = new File(file);
        try(FileInputStream fs = new FileInputStream(file2);
            ObjectInputStream ois = new ObjectInputStream(fs)){
            gameData = (GameData) ois.readObject();
            ois.close();
            fs.close();
            System.out.println("Jogo restaurado com sucesso.");
        }catch (Exception e){
            // e.printStackTrace();
            System.out.println("Erro ao abrir ficheiro um novo jogo vai ser criado");
            this.gameData = new GameData();
        }finally {
            file2.delete();
        }

    }
}
