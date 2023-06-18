package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * class representing the Inky Ghost
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public class Inky extends Ghost implements IMazeElement, Serializable {
    private static final char symbol = 'I';
    private int nextCornerX ;
    private int nextCornerY ;
    private int cornerIndex;
    private double distanceThreshold;
    private int[][] corners; // coordenadas dos cantos do labirinto

    /**
     * Default ghost constructor
     * @param (x, y, direction, maze, speed) all the ghost properties
     *
     */
    public Inky(int x, int y, Direction direction, MazeControl maze, int speed) {
        super(x, y, direction, maze, speed);
        this.direction = Direction.RIGHT;
        this.corners = new int[][]{
                {maze.getHeight(), maze.getWidth()},
                {maze.getHeight(), 0} ,
                {0, maze.getWidth()},
                {0, 0}
        };
        this.cornerIndex = 0;
        this.distanceThreshold = maze.getHeight() * 0.15;
        this.nextCornerX = corners[cornerIndex][0]; // Coordenada X do próximo canto de destino
        this.nextCornerY = corners[cornerIndex][1]; // Coordenada Y do próximo canto de destino
        this.roadMade = new ArrayList<>();
        this.isOut = false;
    }

    /**
     * Function to calculate distance between two points
     * @param (x1, x2, y1, y2) two points coords
     * @return distance
     */
    private double distanceToTarget(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Movement of Inky
     *
     */
    public void move() {
        if(!isOut){
            getOut();
            return;
        }
        int lastX = x;
        int lastY = y;
        /// verifica se o fantasma chegou ao canto pretendido
        if (x == (corners[cornerIndex][0]) && y == (corners[cornerIndex][1])) {
            if(cornerIndex == 0 && nextCornerX == maze.getGhostGate().getX()){
                for(int i = 0 ; i < corners.length ;i++){
                    corners[cornerIndex][0] = corners[cornerIndex +1][0];
                    corners[cornerIndex][1] = corners[cornerIndex +1][1];
                }
                cornerIndex = 0;
            }
            cornerIndex = (cornerIndex + 1) % corners.length; // avança para o próximo canto
        }

        // verifica se o fantasma está muito próximo do canto pretendido
        int distanceToCorner = (int) distanceToTarget(x, y, nextCornerX, nextCornerY);
        if (distanceToCorner <= distanceThreshold && nextCornerX != maze.getGhostGate().getX()) {
            cornerIndex = (cornerIndex + 1) % corners.length; // avança para o próximo canto
            nextCornerX = corners[cornerIndex][0];
            nextCornerY = corners[cornerIndex][1];
        }


        // Verifica se pode seguir na direção atual
        Position currentPosition = new Position(this.x, this.y);
        Position newPos = getNextPosition(currentPosition, direction);

        if (!maze.checkIfWallGhost(newPos.getX(), newPos.getY())) {
            x = newPos.getX();
            y = newPos.getY();
        } else {
            int minDist = Integer.MAX_VALUE;
            Direction bestDirection = null;
            for (Direction d : Direction.values()) {
                currentPosition  = new Position(this.x ,this.y);
                newPos = getNextPosition(currentPosition, d);

                if (!maze.checkIfWallGhost(newPos.getX(), newPos.getY())) {
                    int distToCorner = (int) distanceToTarget(newPos.getX(), newPos.getY(), nextCornerX, nextCornerY);
                    if (distToCorner < minDist && d != Direction.opositeDirection(direction)) {
                        minDist = distToCorner;
                        bestDirection = d;
                    }
                }
            }

            if (bestDirection != null ) {
                direction = bestDirection;
                currentPosition = getNextPosition(currentPosition, direction);
                x = currentPosition.getX();
                y = currentPosition.getY();
            }
        }

        if(road_index < 0) {
            road_index = 0;
        }

        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'C' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }
        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,this);
        road_index++;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
