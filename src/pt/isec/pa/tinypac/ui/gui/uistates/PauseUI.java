package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        btnSave.setMinWidth(100);
        btnResume = new Button("Resume");
        btnResume.setMinWidth(100);
        btnExit  = new Button("Exit");
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
            Platform.exit();
        });
        btnSave.setOnAction(event->{
            gameManager.save();
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
