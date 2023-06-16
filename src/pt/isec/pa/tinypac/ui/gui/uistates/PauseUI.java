package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class PauseUI extends BorderPane {
    GameManager gameManager;
    Button btnResume,btnExit, btnSave;

    public PauseUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        btnSave = new Button("Save");
        btnSave.setStyle(" -fx-text-fill: blue;");
        btnSave.setMinWidth(100);
        btnResume = new Button("Resume");
        btnResume.setStyle(" -fx-text-fill: orange;");
        btnResume.setMinWidth(100);
        btnExit  = new Button("Exit");
        btnExit.setStyle(" -fx-text-fill: red;");
        btnExit.setMinWidth(100);
        HBox hBox = new HBox(btnResume,btnSave,btnExit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        this.setCenter(hBox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
        btnResume.setOnAction( event -> {
            gameManager.pause();
        });
        btnExit.setOnAction( event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EXIT");
            alert.setHeaderText("Do you want to exit?");

            ButtonType btnYes = new ButtonType("YES");
            ButtonType btnNo = new ButtonType("NO");

            alert.getButtonTypes().setAll(btnYes, btnNo);

            alert.showAndWait().ifPresent(e -> {
                if (e == btnYes) {
                    Platform.exit();
                }
            });

        });
        btnSave.setOnAction(event->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(gameManager.save()) {
                alert.setTitle("Congratulations");
                alert.setHeaderText("Your changes have been successfully saved!");
            }
            ButtonType btnOK = new ButtonType("OK");
            alert.getButtonTypes().setAll(btnOK);
            alert.showAndWait().ifPresent(e -> {
                if (e == btnOK) {
                    Platform.exit();
                }
            });
        });
    }

    private void update() {
        if (gameManager.getState() != GameStates.PAUSE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
