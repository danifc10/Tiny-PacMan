package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.uistates.*;

public class RootPane extends BorderPane {
    GameManager gameManager;

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
        ); /// mencionar a possibilidade de apenas ir criando quando muda de estado

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
                gameManager.changePacManDirection(3);
            } else if (keyCode == KeyCode.DOWN) {
                gameManager.changePacManDirection(4);
            } else if (keyCode == KeyCode.LEFT) {
                gameManager.changePacManDirection(2);
            } else if (keyCode == KeyCode.RIGHT) {
                gameManager.changePacManDirection(1);
            } else if (keyCode == KeyCode.SPACE || keyCode == KeyCode.ESCAPE) {
                if(gameManager.getState() != GameStates.PAUSE)
                    gameManager.pause();
                else
                    gameManager.resume();
            }
        });
    }

    private void update() {
        this.setRight(null);
        this.setBottom(null);
    }
}