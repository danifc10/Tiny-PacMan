package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.GameManager;
import pt.isec.pa.tinypac.model.fsm.GameStates;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class MazeUI extends TilePane {
    private static final int CELL_SIZE = 16;
    GameManager gameManager;

    public MazeUI(GameManager gameManager){
        this.gameManager = gameManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void registerHandlers() {
        gameManager.addPropertyChangeListener(evt -> { update(); });
    }

    private void update() {
        getChildren().clear();
        char[][] map = gameManager.getMaze();
        int numRows = map.length;
        int numCols = map[0].length;

        setPrefColumns(numCols);
        setPrefRows(numRows);

        setMaxSize(CELL_SIZE * numCols, CELL_SIZE * numRows);
        setMinSize(CELL_SIZE * numCols, CELL_SIZE * numRows);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Image image = getImageForCell(map[row][col]);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(CELL_SIZE);
                imageView.setFitWidth(CELL_SIZE);
                getChildren().add(imageView);
            }
        }
    }

    private Image getImageForCell(char cell) {
        String imageName;
        switch (cell) {
            case 'K':
                if(gameManager.getState() == GameStates.VULNERABLE)
                    imageName = "blueGhost.png";
                else
                    imageName = "pinky.png";
                break;
            case 'B':
                if(gameManager.getState() == GameStates.VULNERABLE)
                    imageName = "blueGhost.png";
                else
                    imageName = "blinky.png";
                break;
            case 'C':
                if(gameManager.getState() == GameStates.VULNERABLE)
                    imageName = "blueGhost.png";
                else
                    imageName = "clyde.png";
                break;
            case 'P':
                imageName = "pacman.png";
                break;
            case 'I':
                if(gameManager.getState() == GameStates.VULNERABLE)
                    imageName = "blueGhost.png";
                else
                    imageName = "inky.png";
                break;
            case 'x':
                imageName = "wall.png";
                break;
            case 'o':
                imageName = "pellet.png";
                break;
            case 'O':
                imageName = "powerDot.png";
                break;
            case 'M':
                imageName = "pacman.png";
                break;
            case 'W':
                imageName = "warp.png";
                break;
            case 'F':
                imageName = "fruit.png";
                break;
            default:
                imageName = "empty.png";
                break;
        }

        return ImageManager.getImage(imageName);
    }
}
