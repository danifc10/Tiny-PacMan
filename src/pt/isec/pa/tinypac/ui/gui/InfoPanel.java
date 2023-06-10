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
    HBox hbPoints,hbLife, hbTime, hbLevel;

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
        hbPoints.setAlignment(Pos.CENTER);
        hbPoints.setPrefWidth(Integer.MAX_VALUE);
        hbPoints.setSpacing(10);

        hbLife = new HBox();
        hbLife.setAlignment(Pos.CENTER);
        hbLife.setPrefWidth(Integer.MAX_VALUE);

        hbTime = new HBox();
        hbTime.setAlignment(Pos.CENTER);
        hbTime.setPrefWidth(Integer.MAX_VALUE);
        hbTime.setPadding(new Insets(10));

        hbLevel = new HBox();
        hbLevel.setAlignment(Pos.CENTER);
        hbLevel.setPrefWidth(Integer.MAX_VALUE);
        hbLevel.setPadding(new Insets(10));

        this.getChildren().addAll(hbPoints,hbTime, hbLevel, hbLife);

    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {

        hbPoints.getChildren().clear();
        int points = gameManager.getPoints();
        Label lblPoints = new Label("Points: " + points);
        lblPoints.setStyle(" -fx-text-fill: white; ");
        hbPoints.getChildren().add(lblPoints);


        hbTime.getChildren().clear();
        int time = gameManager.getTime();
        Label lblTime = new Label("Time: " + time);
        lblTime.setStyle(" -fx-text-fill: white; ");
        hbTime.getChildren().add(lblTime);


        hbLife.getChildren().clear();
        int life = gameManager.getLife();
        for (int i = 0; i < life; i++) {
            ImageView imageView = new ImageView(ImageManager.getImage("pacman.png"));
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            hbLife.setSpacing(10);
            hbLife.getChildren().add(imageView);
        }

        hbLevel.getChildren().clear();
        int level = gameManager.getLevel();
        Label lblLevel = new Label("Level: "+ level);
        lblLevel.setStyle(" -fx-text-fill: white");
        hbLevel.getChildren().add(lblLevel);


    }
}
