package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.uistates.*;
import pt.isec.pa.tinypac.utils.Direction;
/**
 * RootPane class
 *
 * @author Daniela Correia
 * @version 1.0.0
 */
public class RootPane extends BorderPane {
    GameManager gameManager;

    /**
     * Default constructor
     * @param gameManager reference to gameManager
     */
    public RootPane(GameManager gameManager) {
        this.gameManager = gameManager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setFocusTraversable(true);
        CSSManager.applyCSS(this,"styles.css");

        StackPane stackPane = new StackPane(
                new InitialUI(gameManager),
                new PlayingUI(gameManager),
                new PauseUI(gameManager),
                new VulnerableUI(gameManager),
                new GameOverUI(gameManager),
                new WinUI(gameManager)
        );

        stackPane.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background.png"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,true,false)
                        )
                )
        );

        this.setCenter(stackPane);
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> update());

        this.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.UP) {
                gameManager.changePacManDirection(Direction.UP);
            } else if (keyCode == KeyCode.DOWN) {
                gameManager.changePacManDirection(Direction.DOWN);
            } else if (keyCode == KeyCode.LEFT) {
                gameManager.changePacManDirection(Direction.LEFT);
            } else if (keyCode == KeyCode.RIGHT) {
                gameManager.changePacManDirection(Direction.RIGHT);
            } else if (keyCode == KeyCode.SPACE || keyCode == KeyCode.ESCAPE) {
                if(gameManager.getState() != GameStates.PAUSE)
                    gameManager.pause();
            }
        });
    }

    private void update() {
        this.setRight(null);
        this.setBottom(null);
    }
}