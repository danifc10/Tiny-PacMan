package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

import java.util.HashMap;
import java.util.Map;

public class GameContext  {
    private Map<Integer, String> levelsFile = new HashMap<>();
    private IGameStates currentState;
    private Ghost []ghosts;
    private MazeControl maze;
    private PacMan pacMan;
    private int points = 0;
    private int time  = 0;
    private int level = 0;
    private IGameEngine gameEngine;

    public GameContext(PacMan pacMan, Ghost []ghosts, MazeControl maze, IGameEngine gameEngine){
        putLevelsFile();
        this.gameEngine = gameEngine;
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
        this.level = 1;
        this.currentState = new InitialState(this, pacMan,maze,ghosts, gameEngine);
    }

    public void putLevelsFile() {
        for(int i = 1 ; i <= 20 ; i++){
            if(i < 10)
                this.levelsFile.put(i, "Level10" + i + ".txt");
            else
                this.levelsFile.put(i, "Level1" + i + ".txt");
        }
    }

    public String getFileLevel(){
        return levelsFile.get(this.level);
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
    public void startGame(int direction){
        currentState.startGame(direction);
    }
    public boolean setGhostsFree(){
        return currentState.setGhostsFree();
    }
    public boolean checkIfGhostsOut(){
        return currentState.checkIfGhostsOut();
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
    public boolean wrapZone(int x, int y) {
        return currentState.wrapZone(x, y);
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
    public boolean endGame(){
        return currentState.endGame();
    }
    public boolean endVulnerableTime(){
        return currentState.endVulnerableTime();
    }
   //public void update(int direction){currentState.update(direction);}

    // dados
    public int getPoint(){
        return points;
    }
    public void setPoints(int num){
        points+=num;
    }
    public int getTime(){
        return time;
    }
    public int getLevel(){
        return level;
    }
    public void setMaze(MazeControl maze1){
        this.maze = maze1;
    }
    public MazeControl getMaze(){ return this.maze;}
    public void setLevel(int level){
        this.level = level;
    }
}
