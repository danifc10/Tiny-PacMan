package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.model.fsm.GameContext;

import java.io.IOException;

public class GameUI{
    Terminal terminal;
    GameContext fsm;

    public GameUI(GameContext fsm, Terminal terminal) throws IOException {
        this.fsm = fsm;
        this.terminal = terminal;
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

    void initialUI() throws IOException {
        fsm.startGame();
    }

    void playingUI() throws IOException {
        fsm.update(terminal);
        terminal.setCursorPosition(20, 45);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("POINTS:" + fsm.getPoint());
        terminal.setCursorPosition(20, 46);
        terminal.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        terminal.putString("TIME:" + fsm.getTime());
        terminal.flush();
    }

    void gameOverUI() throws IOException {
        finish = true;
    }

    void winUI(){
        finish = true;
    }
}