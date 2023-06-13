package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.ui.gui.MainJFX;
/**
 * Main class
 * <p>
 * The first class of this project
 *
 * @ author Daniela Correia
 *
 */
public class Main {
    /**
     * Reference to the gameManager object
     */
    public static GameManager gameManager;
    static {
        gameManager = new GameManager();
    }
    /**
     * The program starts here
     * @param args arguments received from the command line
     */
    public static void main(String[] args) {
        Application.launch(MainJFX.class,args);
    }
}