package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class WinUI extends BorderPane {
    GameManager gameManager;
    Button btnExit, btnEnter;
    TextField nameField;

    public WinUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        showTop5();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.getChildren().clear();
        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("win.png"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );
        btnExit = new Button("Exit");
        btnExit.setStyle(" -fx-background-color: white; -fx-text-fill: RED; -fx-font-family: 'Showcard Gothic';");
        btnExit.setMinWidth(100);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(btnExit);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });

        btnExit.setOnAction( event -> {
            Platform.exit();
        });

        btnEnter.setOnAction(evt->{
            // register Top5
            gameManager.registerPoints(nameField.getText());
            createViews();
        });
    }

    private void showTop5(){
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
        VBox vBox1 = new VBox(10); // 10 é o espaçamento vertical entre os componentes
        vBox1.setPadding(new Insets(10)); // Definir o espaçamento interno do VBox
        vBox1.getChildren().addAll(nameLabel, nameField, btnEnter); // Adicionar a Label e o TextField ao VBox
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(10);
        this.setCenter(vBox1);
    }

    private void update() {
        if(gameManager.canReachTop5()) {
            showTop5();
        }
        if (gameManager.getState() != GameStates.WIN) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
