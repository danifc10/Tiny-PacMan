package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.elements.Ghost;
import pt.isec.pa.tinypac.model.fsm.elements.PacMan;
import pt.isec.pa.tinypac.model.fsm.states.InitialState;

public class GameContext {
    private IGameStates currentState;
    private Ghost []ghosts;
    private Maze maze;
    private PacMan pacMan;
    private int points = 0;

    public GameContext(PacMan pacMan, Ghost []ghosts, Maze maze){
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.maze = maze;
        this.currentState = new InitialState(this, pacMan,maze,ghosts);
    }

    void changeState(IGameStates newState){
        this.currentState = newState;
    }

    public boolean startGame(){
        return currentState.startGame();
    }

    public IGameStates eatPoint() {
        return currentState.eatPoint();
    }

    public IGameStates eatFruit(){
        return currentState.eatFruit();
    }
    public IGameStates eatPower(){
        return currentState.eatPower();
    }
    public IGameStates eatGhost(){
        return currentState.eatGhost();
    }

    public IGameStates wrapZone() {
        return currentState.wrapZone();
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

    public GameStates getState(){
        return this.currentState.getState();
    }
}
