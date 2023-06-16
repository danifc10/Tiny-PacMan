package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.*;
import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.List;
/**
 * class where all the data is managed
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GameData implements Serializable {
    MazeControl mazeControl;
    PacMan pacMan;
    Ghost[] ghosts;
    private int totalPoints = 0;
    private int level = 1;
    private int countFruitPoints = 0;
    private int countPoints = 0; // when 20 set new fruit
    private int time = 0;
    private int pacManLife = 3;
    private int numOfGhosts = 4;
    private int speed = 0;
    private boolean stopTimer;
    private int countGhostsPoints = 0;
    private int pacManFood = 0;
    private GameStates LastPauseState = null;

    /**
     * Default constructor
     * <p>
     *
     */
    public GameData() {
        initGame();
    }

    /**
     * Start all the variables and objects for the game start
     *
     */
    public void initGame() {
        pacManFood = 0;
        countPoints = 0;
        numOfGhosts = 4;
        countGhostsPoints = 0;
        time = 0;
        mazeControl = new MazeControl(level);
        List<Position> positions = mazeControl.getGhostStartPositions();
        pacMan = new PacMan(mazeControl.getPacManStart().getX(), mazeControl.getPacManStart().getY(), null,  mazeControl);
        ghosts = new Ghost[]{
                new Blinky( positions.get(0).getX(), positions.get(0).getY(), null,  mazeControl, speed++),
                new Pinky(positions.get(1).getX(), positions.get(1).getY(), null,  mazeControl, speed++),
                new Inky(positions.get(2).getX(), positions.get(2).getY(), null,  mazeControl, speed++),
                new Clyde(positions.get(3).getX(), positions.get(3).getY(), null, mazeControl, speed++)
        };
    }

    /**
     * Get game points
     * @return totalPoints - int with the total of the points
     */
    public int getPoints() {
        return  totalPoints;
    }
    /**
     * Get level
     * @return level - level of this game (int)
     */
    public int getLevel() {
        return level;
    }
    /**
     * Get maze
     * @return char[][] - maze
     */
    public char[][] getMaze() {
        return mazeControl.getMazeControl();
    }
    /**
     * Get pacman life
     * @return pacManLife - integer pacman life
     */
    public int getPacManLife() {
        return pacManLife;
    }
    /**
     * Counter for time
     *
     */
    public void timer() {
        if(!stopTimer)
            time++;
    }
    /**
     * Get time
     * @return time - integer time
     */
    public int getTime(){
        return time;
    }
    /**
     * Get number of ghosts alive
     * @return numOfGhosts - integer for number of ghosts alive
     */
    public int getNumGhosts() {
        return numOfGhosts;
    }

    // ghosts things

    /**
     * Set points when eat a ghost
     *
     */
    public void setGhostsPoints(){
        countGhostsPoints++;
        this.numOfGhosts--;
        totalPoints = totalPoints + (50 * countGhostsPoints);
    }
    /**
     * Set true the vulnerability of ghosts
     *
     */
    public void setVulnerable() {
        for(Ghost g : ghosts){
            g.setVulnerable(true);
        }
    }
    /**
     * Set false the vulnerability of ghosts
     *
     */
    public void endVulnerable() {
        for (Ghost g : ghosts) {
            g.setVulnerable(false);
        }
    }
    /**
     * Set ghosts out of the jail
     *
     */
    public void setGhostsFree() {
        for(Ghost g: ghosts){
            g.move();
        }
    }
    /**
     * Normal ghost move and check if pacman is here
     *@return true - if eat the pacman
     *        false - if not eat the pacman
     */
    public boolean moveGhosts() {
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead()) {
                ghost.move();
                if (ghost.getX() == pacMan.getX() && ghost.getY() == pacMan.getY()){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Vulnerable ghost move and check if pacman is here
     *
     */
    public void vulnerableMoveGhosts(){
        for (Ghost ghost : ghosts) {
            if(!ghost.isDead()) {
                ghost.vulnerableMove();
                if (ghost.getX() == pacMan.getX() && ghost.getY() == pacMan.getY()){
                    ghost.setDead(true);
                    mazeControl.removeGhost(ghost.getX(), ghost.getY());
                    setGhostsPoints();
                }
            }
        }
    }

    // pacman things

    /**
     * Set new pacman direction
     * @param direction - Direction for new pacman Direction
     */
    public void setPacManDirection(Direction direction) {
        stopTimer = false;
        if(direction == Direction.RIGHT){
            if(pacMan.canMove(pacMan.getX(), pacMan.getY() + 1))
                pacMan.setDirection(Direction.RIGHT);
        }else if(direction == Direction.LEFT){
           if(pacMan.canMove(pacMan.getX(), pacMan.getY() - 1))
                pacMan.setDirection(Direction.LEFT);
        }else if(direction == Direction.UP){
            if(pacMan.canMove(pacMan.getX() - 1, pacMan.getY()))
                pacMan.setDirection(Direction.UP);
        }else if(direction == Direction.DOWN){
            if (pacMan.canMove(pacMan.getX() + 1, pacMan.getY()))
                pacMan.setDirection(Direction.DOWN);
        }
    }

    /**
     * Normal pacman move and all the checks
     * @return eatenElement - IMazeElement that represent the last element thtat pacman eat
     */
    public IMazeElement movePacMan() {
        int x = pacMan.getX();
        int y = pacMan.getY();

        IMazeElement eatElement = null;
        switch (pacMan.getDirection()) {
            case LEFT -> {
                if (y > 0 && canMove(x, y - 1)) {
                    eatElement = checks(x, y - 1);
                    pacMan.setDirection(Direction.LEFT);
                    pacMan.move();
                }
            }
            case RIGHT -> {
                if (y < mazeControl.getWidth() && canMove(x, y + 1)) {
                    eatElement = checks(x, y + 1);
                    pacMan.setDirection(Direction.RIGHT);
                    pacMan.move();
                }
            }
            case UP -> {
                if (x > 0 && canMove(x - 1, y)) {
                    eatElement = checks(x - 1, y);
                    pacMan.setDirection(Direction.UP);
                    pacMan.move();
                }
            }
            case DOWN -> {
                if (x < mazeControl.getHeight() && canMove(x + 1, y)) {
                    eatElement = checks(x + 1, y);
                    pacMan.setDirection(Direction.DOWN);
                    pacMan.move();
                }
            }
        }

        return eatElement;
    }

    /**
     * When PacMan eat a Ghost
     *
     */
    public void eatGhost(IMazeElement element) {
        for(Ghost g : ghosts){
            if(g.getSymbol() == element.getSymbol() || (g.getX() == pacMan.getX() && g.getY() == pacMan.getY())){
                g.setDead(true);
                mazeControl.remove(g.getLastX(), g.getLastY());
                setGhostsPoints();
            }
        }
    }

    /**
     * Verification for PacMan move
     * @param x, y - next PacMan position
     * @return boolean - true if can move, false if can't
     */
    public boolean canMove(int x, int y) {
        return (!mazeControl.checkIfWall(x, y) && !(mazeControl.getXY(x, y) instanceof GhostGate));
    }

    /**
     * Verification for PacMan move
     * @param x, y - next PacMan position
     * @return element - element eaten by PacMan
     */
    public IMazeElement checks(int x, int y) {
        IMazeElement element = mazeControl.getXY(x, y);
        if (element instanceof Point) {
            countPoints++;
            if (countPoints == 20) {
                mazeControl.setNewFruit();
                countPoints = 0;
            }
            totalPoints++;
            pacManFood++;
        } else if (element instanceof Fruit) {
            countPoints = 0;
            countFruitPoints++;
            totalPoints+= (25*countFruitPoints);
            pacManFood++;
        } else if (element instanceof PowerPoint) {
            totalPoints+=10;
            pacManFood++;
        } else if (element instanceof WarpZone) {
            pacMan.checkIfWarp(x, y);
        }
        return element;
    }

    /**
     * Verification for win
     *
     * @return boolean - true if won, false if not
     */
    public boolean checkIfWin(){
        System.out.println("empty: " + mazeControl.checkWin() + " totalPoints: " + mazeControl.getTotalPoints());
        //return pacManFood >= (mazeControl.getTotalPoints() - 2);

        return( mazeControl.checkWin() + numOfGhosts) == mazeControl.getTotalPoints();
    }

    /**
     * When levels up, set life pacman and init the new level
     */
    public void levelUp() {
        pacManLife = 3;
        stopTimer = true;
        level++;
        initGame();
    }
    /**
     * When game Over , set pacman life and restart the level
     */
    public void gameOver() {
        stopTimer = true;
        pacManLife--;
        initGame();
    }

    /**
     * Save the last state to return when resume game
     * @param  state -GameStates - the last state
     */
    public void setStateOfPause(GameStates state) {
        LastPauseState = state;
    }

    /**
     * Get the state to return game after pause
     * @return LastPauseState
     */
    public GameStates getLastPauseState(){
        return LastPauseState;
    }

    /**
     * Get the current Pac Man direction
     * @return Direction pacManDirection - current pacMan direction
     */
    public Direction getPacManDirection() {
        return pacMan.getDirection();
    }

    /**
     * Get the number of alive ghosts
     * @return int numOfGhosts - number of ghosts
     */
    public int getNumOfGhosts(){
        return numOfGhosts;
    }
}
