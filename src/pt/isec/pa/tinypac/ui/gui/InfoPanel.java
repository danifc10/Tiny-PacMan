package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
/**
 * class for the UI of info panel
 * @author Daniela Correia
 * @version 1.0.0
 */
public class InfoPanel extends HBox {
    GameManager gameManager;
    HBox hbInfo;

    /**
     * Default constructor
     * @param gameManager class GameManager
     */
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

        hbInfo = new HBox();
        hbInfo.setAlignment(Pos.CENTER);
        hbInfo.setPrefWidth(Integer.MAX_VALUE);
        hbInfo.setSpacing(15);

        this.getChildren().addAll(hbInfo);

    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {

        hbInfo.getChildren().clear();
        int points = gameManager.getPoints();
        Label lblPoints = new Label("SCORE");
        Label lblPoints2 = new Label(" "+ points);
        lblPoints2.setStyle(" -fx-text-fill: orange;");

        int time = gameManager.getTime();
        Label lblTime = new Label("TIME");
        Label lblTime2 = new Label(" " + time);
        lblTime2.setStyle(" -fx-text-fill: red;");

        int level = gameManager.getLevel();
        Label lblLevel = new Label("LEVEL");
        Label lblLevel2 = new Label(" "+level);
        lblLevel2.setStyle(" -fx-text-fill: yellow;");

        int numGhosts = gameManager.getNumGhosts();
        Label lblGhost = new Label("Ghosts");
        Label lblGhost2 = new Label(" " + numGhosts);
        lblGhost2.setStyle(" -fx-text-fill: #00b9ff;");

        hbInfo.getChildren().addAll(lblPoints,lblPoints2, lblTime, lblTime2,lblLevel, lblLevel2,lblGhost, lblGhost2);

        int life = gameManager.getLife();
        for (int i = 0; i < life; i++) {
            ImageView imageView = new ImageView(ImageManager.getImage("pacmanR.png"));
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            hbInfo.getChildren().add(imageView);
        }
    }
}
