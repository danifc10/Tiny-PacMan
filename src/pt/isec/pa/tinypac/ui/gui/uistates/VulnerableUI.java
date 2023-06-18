package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.InfoPanel;
/**
 * class for vulnerable UI
 * @author Daniela Correia
 * @version 1.0.0
 */
public class VulnerableUI extends BorderPane {
    GameManager gameManager;
    MazeUI mazeUI;

    /**
     * Default constructor
     * @param gameManager reference to gameManager
     */
    public VulnerableUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setFocusTraversable(true);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().clear();
        mazeUI = new MazeUI(gameManager);
        this.setCenter(mazeUI);
        this.setBottom(new InfoPanel(gameManager));
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {
        if (gameManager.getState() != GameStates.VULNERABLE)
            this.setVisible(false);
        else if(gameManager.getState() == GameStates.VULNERABLE)
            this.setVisible(true);
    }
}
