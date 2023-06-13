package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;

public class GameOverUI extends BorderPane {
    GameManager gameManager;
    Button btnEnter, btnExit, btnPlay;
    TextField nameField;
    MediaPlayer mediaPlayer;
    Media media;

    public GameOverUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        // Criar uma Label para exibir o texto "Player Name"
        Label nameLabel = new Label("Player Name:");
        nameLabel.setStyle(" -fx-text-fill: white; ");
        // Criar um campo de texto para o jogador inserir seu nome
        nameField = new TextField();
        nameField.setMaxWidth(300);
        nameField.setMaxHeight(100);
        btnEnter = new Button("Enter");
        btnEnter.setMinWidth(100);
        // Criar um layout VBox para organizar os componentes
        VBox vBox = new VBox(10); // 10 é o espaçamento vertical entre os componentes
        vBox.setPadding(new Insets(10)); // Definir o espaçamento interno do VBox
        vBox.getChildren().addAll(nameLabel, nameField, btnEnter); // Adicionar a Label e o TextField ao VBox
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
        btnEnter.setOnAction(evt->{
            // register Top5
            gameManager.registerPoints(nameField.getText());
            Platform.exit();
        });
    }

    private void update() {
        if (gameManager.getState() != GameStates.GAME_OVER) {
            this.setVisible(false);
            return;
        }else if(gameManager.getState() == GameStates.GAME_OVER){
            this.setVisible(true);
        }

        if(!gameManager.canReachTop5())
            showGameOver();
    }

    private void showGameOver() {
        Label lbl = new Label("Game Over");
        lbl.setStyle(" -fx-text-fill: white; -fx-font-family: 'Showcard Gothic'; -fx-font-size: 100px");
        btnExit = new Button("Exit");
        btnExit.setMinWidth(100);
        btnPlay = new Button("Play Again");
        btnPlay.setMinWidth(100);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(lbl, btnExit, btnPlay);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);

        btnExit.setOnAction(e->{
            Platform.exit();
        });

        btnPlay.setOnAction(e->{
            gameManager.restart();
        });
    }
}
