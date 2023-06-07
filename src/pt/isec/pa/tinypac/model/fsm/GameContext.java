package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.GameData;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

public class GameContext {
    private GameData gameData;
    private IGameStates currentState;
    private IGameEngine gameEngine;

    public GameContext(){
        this.gameEngine =  new GameEngine();
        gameData = new GameData();
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
        System.out.println("context start");
        currentState.startGame();
        return true;
    }
    public void setPacManNewDirection(int readDirection) {
        currentState.changeDirection(readDirection);
    }
    public boolean pauseGame(){return currentState.pauseGame();}
    public boolean resumeGame(){return currentState.resumeGame();}

    public boolean ghostCollision() {
        return currentState.ghostCollision();
    }
    public boolean restart() {
        return currentState.restart();
    }
    public boolean levelUp() {
        return currentState.levelUp();
    }
    public boolean endVulnerableTime(){
        return currentState.endVulnerableTime();
    }

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
}
