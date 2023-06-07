package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

public class Main {
    public static GameManager gameManager;
    static {
        gameManager = new GameManager();
    }
    public static void main(String[] args) {
        Application.launch(MainJFX.class,args);
    }
}