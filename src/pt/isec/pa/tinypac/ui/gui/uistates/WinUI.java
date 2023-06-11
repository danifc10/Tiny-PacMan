package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class WinUI extends BorderPane {
    GameManager gameManager;
    Button btnExit;

    public WinUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Label lbl = new Label("You complete all levels!   Congratulations!");
        lbl.setStyle(" -fx-text-fill: white; -fx-font-family: 'Showcard Gothic'; -fx-font-size: 40px");

        btnExit = new Button("Exit");
        btnExit.setMinWidth(100);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(lbl, btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });

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
