package pt.isec.pa.tinypac.model.fsm;

import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

import java.io.IOException;

public class GameContext  {
    private IGameStates currentState;
    private Ghost []ghosts;
    private MazeControl maze;
    private PacMan pacMan;
    private int points = 0;
    private int time  = 0;

    public GameContext(PacMan pacMan, Ghost []ghosts, MazeControl maze){
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
        this.currentState = new InitialState(this, pacMan,maze,ghosts);

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
    public void update(Terminal terminal) throws IOException {
        currentState.update(terminal);
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
    public IGameStates wrapZone(int x, int y) {
        return currentState.wrapZone(x, y);
    }
    public IGameStates eatAll() {
        return currentState.eatAll();
    }
    public IGameStates ghostCollision() {
        return currentState.ghostCollision();
    }
    public IGameStates restart() {
        return currentState.restart();
    }
    public IGameStates levelUp() {
        return currentState.levelUp();
    }

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

}
