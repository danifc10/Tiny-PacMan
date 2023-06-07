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

public class PlayingUI extends BorderPane {
    private static final int CELL_SIZE = 16;
    GameManager gameManager;
    MazeUI mazeUI;

    public PlayingUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setFocusTraversable(true);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().clear();
        gameManager.start();
        mazeUI = new MazeUI(gameManager);
        this.setCenter(mazeUI);
        this.setBottom(new InfoPanel(gameManager));
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {
        if (gameManager.getState() != GameStates.PLAYING) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
