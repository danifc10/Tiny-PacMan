package pt.isec.pa.tinypac.ui.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.gameengine.GameEngine;

public class MainJFX extends Application {
    GameManager gameManager;
    GameEngine gameEngine;

    @Override
    public void init() throws Exception {
        super.init();
        gameEngine = new GameEngine();
        gameManager = Main.gameManager;
        gameEngine.registerClient(gameManager);
        gameEngine.start(300);
    }

    @Override
    public void start(Stage stage) throws Exception {
        newStageForTesting(stage,"PAC-MAN");
    }

    private void newStageForTesting(Stage stage, String title) {
        RootPane root = new RootPane(gameManager);
        Scene scene = new Scene(root,1000,600);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.setResizable(false); // nao permite alterar o tamanho da janela
        stage.show();
    }
}