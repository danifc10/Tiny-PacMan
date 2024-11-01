package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.InfoPanel;
import pt.isec.pa.tinypac.utils.Score;

import java.util.List;
/**
 * class for the Initial UI
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class InitialUI extends BorderPane {
    GameManager gameManager;
    Button btnStart,btnExit, btnTop5,  btnReturn;
    VBox top5Pane;
    MazeUI mazeUI;

    /**
     * Default constructor
     * @param gameManager reference to gameManager
     */
    public InitialUI(GameManager gameManager) {
        this.gameManager = gameManager;

        showTop5();
        initialMenu();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });

        btnStart.setOnAction( event -> {
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            this.getChildren().clear();

            gameManager.start();
            mazeUI = new MazeUI(gameManager);
            this.setCenter(mazeUI);
            this.setBottom(new InfoPanel(gameManager));
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

        btnTop5.setOnAction(event->{
            showTop5();
        });
    }

    private void initialMenu(){
        setFocusTraversable(true);
        btnStart = new Button("Start");
        btnStart.setMinWidth(100);
        btnStart.setStyle("-fx-text-fill: blue");
        btnTop5  = new Button("Top5");
        btnTop5.setMinWidth(100);
        btnTop5.setStyle(" -fx-text-fill: #ff5900");
        btnExit  = new Button("Exit");
        btnExit.setMinWidth(100);
        btnExit.setStyle("-fx-text-fill: red");
        VBox hBox = new VBox(btnStart, btnTop5,btnExit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        this.setCenter(hBox);

        registerHandlers();
    }

    private void showTop5() {
        top5Pane = new VBox();
        top5Pane.setPadding(new Insets(10));
        top5Pane.setSpacing(10);
        List<Score> scores = gameManager.getTop5();

        for(int i = 0 ; i < scores.size() ; i++){
            String name = scores.get(i).getName();
            int points = scores.get(i).getPoints();
            Label playerLabel = new Label( (i+1) + ". "+name + "  " + points);
            top5Pane.getChildren().add(playerLabel);
        }

        btnReturn = new Button("Back");
        btnReturn.setStyle(" -fx-text-fill: red;");
        btnReturn.setMinWidth(100);
        btnReturn.setOnAction(e -> initialMenu());
        top5Pane.setAlignment(Pos.CENTER);
        top5Pane.setSpacing(10);
        top5Pane.getChildren().addAll(btnReturn);
        this.setCenter(top5Pane);
    }

    private void update() {
        if (gameManager.getState() != GameStates.INITIAL) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}