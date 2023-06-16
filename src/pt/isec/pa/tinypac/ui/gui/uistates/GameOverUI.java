package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
/**
 * class for the Game over UI
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class GameOverUI extends BorderPane {
    GameManager gameManager;
    Button btnEnter, btnExit, btnPlay;
    TextField nameField;

    /**
     * Default constructor
     * @param gameManager reference to gameManager
     */
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
        btnEnter.setStyle("-fx-font-family: 'Showcard Gothic'");
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
        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("gameOver.png"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                ));

        btnExit = new Button("Exit");
        btnExit.setMinWidth(100);
        btnExit.setStyle(" -fx-text-fill: red");
        btnPlay = new Button("Play Again");
        btnPlay.setMinWidth(100);
        btnPlay.setStyle("-fx-text-fill: blue;");
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(10));
        hBox.getChildren().addAll( btnExit, btnPlay);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.setSpacing(10);
        this.setCenter(hBox);

        btnExit.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("EXIT");
            alert.setHeaderText("Do you want to exit?");

            ButtonType btnYes = new ButtonType("YES");
            ButtonType btnNo = new ButtonType("NO");

            alert.getButtonTypes().setAll(btnYes, btnNo);

            alert.showAndWait().ifPresent(event -> {
                if (event == btnYes) {
                    Platform.exit();
                }
            });
        });

        btnPlay.setOnAction(e->{
            gameManager.restart();
        });
    }
}
