package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class InfoPanel extends HBox {
    GameManager gameManager;
    HBox hbPoints,hbLife, hbTime;

    public InfoPanel(GameManager gameManager) {
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        hbPoints = new HBox();
        hbPoints.setAlignment(Pos.BASELINE_LEFT);
        hbPoints.setPrefWidth(Integer.MAX_VALUE);

        hbLife = new HBox();
        hbLife.setAlignment(Pos.CENTER_RIGHT);
        hbLife.setPrefWidth(Integer.MAX_VALUE);

        hbTime = new HBox();
        hbTime.setAlignment(Pos.BASELINE_CENTER);
        hbTime.setPrefWidth(Integer.MAX_VALUE);

        this.getChildren().addAll(hbPoints,hbLife, hbTime);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {

        hbPoints.getChildren().clear();
        int points = gameManager.getPoints();
        Label lblPoints = new Label("Points: "+ points);
        lblPoints.setStyle("-fx-background-color: yellow;");
        if (points==0) {
            hbPoints.getChildren().add(new Label("-none-"));
            hbPoints.getChildren().get(0).setId("labelnone");
        }else {
            hbPoints.getChildren().add(lblPoints);
        }

        hbTime.getChildren().clear();
        int time = gameManager.getTime();
        Label lblTime = new Label("Time: " + time);
        lblTime.setStyle("-fx-background-color: green;");
        if (points==0) {
            hbPoints.getChildren().add(lblTime);
            hbPoints.getChildren().get(0).setId("labelnone");
        }else {
            hbPoints.getChildren().add(lblTime);
        }

        hbLife.getChildren().clear();
        int life = gameManager.getLife();
        for (int i = 0; i < life; i++) {
            ImageView imageView = new ImageView(ImageManager.getImage("pacman.png"));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            hbLife.getChildren().add(imageView);
        }
    }
}
