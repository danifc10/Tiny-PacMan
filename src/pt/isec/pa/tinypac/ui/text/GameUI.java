package pt.isec.pa.tinypac.ui.text;


import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.fsm.GameContext;
import pt.isec.pa.tinypac.ui.gui.MazeDisplay;

import java.io.IOException;

public class GameUI {
    GameContext fsm;
    public GameUI(GameContext fsm) {
        this.fsm = fsm;
    }

    boolean finish = false;
    public void start() throws IOException {
        while(!finish){
            switch (fsm.getState()){
                case INITIAL -> initialUI();
                case PLAYING -> playingUI();
                case GAME_OVER -> gameOverUI();
                case WIN -> winUI();
            }
        }
    }

    void initialUI(){
        fsm.startGame();
    }

    void gameOverUI(){
    }

    void playingUI() throws IOException {

        Maze maze = new Maze(31, 29, "Level101.txt");
        MazeDisplay display;
        try {
            display = new MazeDisplay(maze);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        display.paint(maze);

    }

    void winUI(){

    }
}