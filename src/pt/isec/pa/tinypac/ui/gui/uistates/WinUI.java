package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class WinUI extends BorderPane {
    GameManager gameManager;
    Button btnStart,btnExit;

    public WinUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnStart = new Button("Start");
        btnStart.setMinWidth(100);
        btnExit  = new Button("Exit");
        btnExit.setMinWidth(100);
        HBox hBox = new HBox(btnStart,btnExit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        this.setCenter(hBox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
        btnStart.setOnAction( event -> {
            gameManager.start();
        });
        btnExit.setOnAction( event -> {
            Platform.exit();
        });
    }

    private void update() {
        if (gameManager.getState() != GameStates.WIN) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
