package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

public class GameContext {
    private IGameStates currentState;
    private Ghost []ghosts;
    private MazeControl maze;
    private PacMan pacMan;
    private int points = 0;
    private int level = 0;
    private IGameEngine gameEngine;

    public GameContext(MazeControl maze, IGameEngine gameEngine, PacMan pacMan, Ghost [] ghosts){
        this.gameEngine = gameEngine;
        this.maze = maze;
        this.level = 1;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.currentState = new InitialState(this, pacMan,maze,ghosts, gameEngine);
    }

    public GameStates getState(){
        return currentState.getState();
    }

    void changeState(IGameStates newState){
        this.currentState = newState;
    }

    // tranciçoes
    public boolean pauseGame(){return currentState.pauseGame();}
    public boolean resumeGame(){return currentState.resumeGame();}
    public void startGame(){
        currentState.startGame();
    }
    public boolean setGhostsFree(){
        return currentState.setGhostsFree();
    }
    public boolean eatPoint() {
        return currentState.eatPoint();
    }
    public boolean eatFruit(){
        return currentState.eatFruit();
    }
    public boolean eatPower(){
        return currentState.eatPower();
    }
    public boolean eatGhost(){
        return currentState.eatGhost();
    }
    public boolean ifEatAll() {
        return currentState.ifEatAll();
    }
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
    public int getPoint(){
        return points;
    }
    public void setPoints(int num){
        points+=num;
    }
    public void resetPoints(){
        points = 0;
    }
    public int getLevel(){
        return level;
    }
    public void setMaze(MazeControl maze){
        this.maze = maze;
    }
    public MazeControl getMaze(){ return this.maze;}
    public void setLevel(int level){
        this.level = level;
    }
    public void setPacMan(PacMan p){
        this.pacMan = p;
    }
}
