package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

public class GameContext  {

    private IGameStates currentState;
    private Ghost []ghosts;
    private MazeControl maze;
    private PacMan pacMan;
    private int points = 0;
    private int time  = 0;
    private int level = 0;

    public GameContext(PacMan pacMan, Ghost []ghosts, MazeControl maze){
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
        this.currentState = new InitialState(this, pacMan,maze,ghosts);
        this.level = 1;
    }

    public GameStates getState(){
        return currentState.getState();
    }
    void changeState(IGameStates newState){
        this.currentState = newState;
    }

    // tranciçoes
    public void startGame(){
        currentState.startGame();
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
   // public void update(int direction){currentState.update(direction);}

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
    public MazeControl getMaze(){ return maze;}
    public void setLevel(int level){
        this.level = level;
    }
}
