package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.InfoPanel;

import java.io.File;

public class PlayingUI extends BorderPane  {
    GameManager gameManager;
    MazeUI mazeUI;
    MediaPlayer mediaPlayer;

    public PlayingUI(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        String soundPath = "src/pt/isec/pa/tinypac/ui/gui/resources/sounds/pacman_playing.m4a";
        Media media = new Media(new File(soundPath).toURI().toString().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.ZERO);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
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
            mediaPlayer.stop();
        }else if(gameManager.getState() == GameStates.PLAYING){
            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            this.setVisible(true);
        }

    }
}
