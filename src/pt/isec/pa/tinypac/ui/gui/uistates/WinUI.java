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
 * class for win UI
 * @author Daniela Correia
 * @version 1.0.0
 */
public class WinUI extends BorderPane {
    GameManager gameManager;
    Button btnExit, btnEnter;
    TextField nameField;

    /**
     * Default constructor
     * @param gameManager reference to gameManager
     */
    public WinUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        if(gameManager.canReachTop5()) {
            do {
                // Criar uma Label para exibir o texto "Player Name"
                Label nameLabel = new Label("Player Name:");
                // Criar um campo de texto para o jogador inserir seu nome
                nameField = new TextField();
                nameField.setMaxWidth(300);
                nameField.setMaxHeight(100);

                btnEnter = new Button("Enter");
                btnEnter.setMinWidth(100);
                // Criar um layout VBox para organizar os componentes
                VBox vBox1 = new VBox(10); // 10 é o espaçamento vertical entre os componentes
                vBox1.setPadding(new Insets(10)); // Definir o espaçamento interno do VBox
                vBox1.getChildren().addAll(nameLabel, nameField, btnEnter); // Adicionar a Label e o TextField ao VBox
                vBox1.setAlignment(Pos.CENTER);
                vBox1.setSpacing(10);
                this.setCenter(vBox1);
            }while(!gameManager.checkName(nameField.getText()));
        }
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });

        btnEnter.setOnAction(evt->{
            // register Top5
            if(gameManager.checkName(nameField.getText())) {
                gameManager.registerPoints(nameField.getText());
                showWin();
            }
        });
    }

    private void showWin(){
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
        btnExit.setStyle("-fx-text-fill: RED;");
        btnExit.setMinWidth(100);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(btnExit);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setSpacing(10);
        this.setCenter(vBox);

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
    }

    private void update() {
        if(!gameManager.canReachTop5())
            showWin();
        if (gameManager.getState() != GameStates.WIN)
            this.setVisible(false);
        else
            this.setVisible(true);
    }
}
