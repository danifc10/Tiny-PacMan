package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.Score;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.InfoPanel;

import java.io.File;
import java.util.List;

public class InitialUI extends BorderPane {
    GameManager gameManager;
    Button btnStart,btnExit, btnTop5,  btnReturn;
    VBox top5Pane;
    MazeUI mazeUI;
    MediaPlayer mediaPlayer;

    String soundPath = "src/pt/isec/pa/tinypac/ui/gui/resources/sounds/pacman_beginning.mp3";
    Media media = new Media(new File(soundPath).toURI().toString().toString());

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
            Platform.exit();
        });

        btnTop5.setOnAction(event->{
            showTop5();
        });
    }

    private void initialMenu(){

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.ZERO);

        setFocusTraversable(true);
        btnStart = new Button("Start");
        btnStart.setMinWidth(100);
        btnStart.setStyle("-fx-font-family: 'Showcard Gothic'; -fx-text-fill: blue");
        btnTop5  = new Button("Top5");
        btnTop5.setMinWidth(100);
        btnTop5.setStyle("-fx-font-family: 'Showcard Gothic'; -fx-text-fill: #ff5900");
        btnExit  = new Button("Exit");
        btnExit.setMinWidth(100);
        btnExit.setStyle("-fx-font-family: 'Showcard Gothic'; -fx-text-fill: red");
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
            playerLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Showcard Gothic';");
            top5Pane.getChildren().add(playerLabel);
        }

        btnReturn = new Button("Back");
        btnReturn.setStyle(" -fx-background-color: white; -fx-text-fill: red;-fx-font-family: 'Showcard Gothic';");
        btnReturn.setMinWidth(100);
        btnReturn.setOnAction(e -> initialMenu());
        top5Pane.setAlignment(Pos.CENTER);
        top5Pane.setSpacing(10);
        top5Pane.getChildren().addAll(btnReturn);
        this.setCenter(top5Pane);
    }

    private void update() {
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        if (gameManager.getState() != GameStates.INITIAL) {
            this.setVisible(false);
            mediaPlayer.stop();
            return;
        }
        this.setVisible(true);
    }
}