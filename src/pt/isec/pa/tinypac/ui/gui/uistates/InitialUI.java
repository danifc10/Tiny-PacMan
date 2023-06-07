package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.InfoPanel;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class InitialUI extends BorderPane {
    private static final int CELL_SIZE = 16;
    GameManager gameManager;
    Button btnStart,btnExit, btnCredits, btnTop5,  btnReturn;
    VBox top5Pane, creditsPane;
    MazeUI mazeUI;

    public InitialUI(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setFocusTraversable(true);
        btnStart = new Button("Start");
        btnStart.setMinWidth(100);
        btnCredits  = new Button("Credits");
        btnCredits.setMinWidth(100);
        btnTop5  = new Button("Top5");
        btnTop5.setMinWidth(100);
        btnExit  = new Button("Exit");
        btnExit.setMinWidth(100);
        VBox hBox = new VBox(btnStart, btnCredits, btnTop5,btnExit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        this.setCenter(hBox);
        btnTop5.setDisable(false);
        btnCredits.setDisable(false);
        btnExit.setDisable(false);
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
        btnCredits.setOnAction(event ->{
            showCredits();
        });
        btnTop5.setOnAction(event->{
            showTop5();
        });
    }

    private void showTop5() {
        top5Pane = new VBox();
        top5Pane.setPadding(new Insets(10));
        top5Pane.setSpacing(10);

        Label titleLabel = new Label("Top 5 Players");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white");

        Label player1Label = new Label("1. John");
        Label player2Label = new Label("2. Sarah");
        Label player3Label = new Label("3. Mike");
        Label player4Label = new Label("4. Emily");
        Label player5Label = new Label("5. Alex");
        player1Label.setStyle(" -fx-text-fill: white");
        player2Label.setStyle(" -fx-text-fill: white");
        player3Label.setStyle(" -fx-text-fill: white");
        player4Label.setStyle(" -fx-text-fill: white");
        player5Label.setStyle(" -fx-text-fill: white");

        btnReturn = new Button("Back");
        btnReturn.setMinWidth(100);
        btnReturn.setOnAction(e -> createViews());
        top5Pane.setAlignment(Pos.CENTER);
        top5Pane.setSpacing(10);
        top5Pane.getChildren().addAll(titleLabel, player1Label, player2Label, player3Label, player4Label, player5Label, btnReturn);
        this.setCenter(top5Pane);
    }

    private void showCredits() {
        creditsPane = new VBox();
        ImageView imageView = new ImageView(ImageManager.getImage("isec.png"));
        imageView.setX(0);
        imageView.setFitHeight(90);
        imageView.setFitWidth(180);
        Label nameLabel = new Label("Daniela Fernandes Correia - a2021143404");
        Label deisLabel = new Label("            DEIS - ISEC   ");
        nameLabel.setStyle(" -fx-text-fill: white");
        deisLabel.setStyle(" -fx-text-fill: white");
        btnReturn = new Button("Back");
        btnReturn.setOnAction(e -> createViews());

        creditsPane.setAlignment(Pos.CENTER);
        creditsPane.setSpacing(10);
        creditsPane.getChildren().addAll(imageView, nameLabel, deisLabel,btnReturn);
        this.setCenter(creditsPane);
    }

    private void update() {
        if (gameManager.getState() != GameStates.INITIAL) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}