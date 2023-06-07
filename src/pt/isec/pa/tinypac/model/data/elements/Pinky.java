package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.maze.IMazeElement;
import pt.isec.pa.tinypac.model.data.maze.MazeControl;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public class Pinky extends Ghost {
    private static final char symbol = 'K';
    private IMazeElement symbolRemove =null;
    private int nextCornerX ;
    private int nextCornerY ;
    private int cornerIndex;
    private double distanceThreshold; // distancia minima definida
    private int[][] corners; // coordenadas dos cantos do labirinto
    private final int[] dx = {-1, 1, 0, 0}; // movimento de X para cada direção
    private final int[] dy = {0, 0, 1, -1}; // movimento de Y para cada direção

    public Pinky(int x, int y, int direction, MazeControl maze, int speed) {
        super(x, y, direction, maze, speed);
        this.direction = 2;
        this.corners = new int[][]{{0, maze.getWidth() }, {maze.getWidth(), maze.getHeight()}, {0, 0},{maze.getWidth(), 0} };
        this.cornerIndex = 0;
        this.distanceThreshold = maze.getHeight() * 0.15;
        this.nextCornerX = corners[cornerIndex][0]; // Coordenada X do próximo canto de destino
        this.nextCornerY = corners[cornerIndex][1]; // Coordenada Y do próximo canto de destino
        this.roadMade = new ArrayList<>();
        this.isOut = false;
    }

    public Pinky(){};

    private double distanceToTarget(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void move() {
        int lastX = x;
        int lastY = y;

        /// verifica se o fantasma chegou ao canto pretendido
        if (x == (corners[cornerIndex][0] - 1) && y == (corners[cornerIndex][1] -1)) {
            cornerIndex = (cornerIndex + 1) % corners.length; // avança para o próximo canto
        }

        // verifica se o fantasma está muito próximo do canto pretendido
        int distanceToCorner = (int) distanceToTarget(x, y, nextCornerX, nextCornerY);
        if (distanceToCorner <= distanceThreshold) {
            cornerIndex = (cornerIndex + 1) % corners.length; // avança para o próximo canto
            nextCornerX = corners[cornerIndex][0];
            nextCornerY = corners[cornerIndex][1];
        }

        // Verifica se pode seguir na direção atual
        int nextX = x + dx[direction];
        int nextY = y + dy[direction];

        if (!maze.checkIfWallGhost(nextX, nextY) && maze.getXY(x, y).getSymbol() == 'Y') {
            x = nextX;
            y = nextY;
        } else {
            int minDist = Integer.MAX_VALUE;
            int bestDirection = -1;
            for (int d = 0; d < 4; d++) {
                int newX = x + dx[d];
                int newY = y + dy[d];

                if (!maze.checkIfWallGhost(newX, newY)) {
                    int distToCorner = (int) distanceToTarget(newX, newY, nextCornerX, nextCornerY);
                    if (distToCorner < minDist && d != getOpositeDirection(direction)) {
                        minDist = distToCorner;
                        bestDirection = d;
                    }
                }
            }

            if (bestDirection != -1 ) {
                direction = bestDirection;
                x += dx[direction];
                y += dy[direction];
            }
        }

        if(road_index < 0) {
            road_index = 0;
        }
        roadMade.add(road_index, new Position(x, y));
        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
            maze.setXY(lastX, lastY, symbolRemove);
        }
        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,new Pinky());
        road_index++;
    }

    @Override
    public void vulnerableMove() {
        --road_index;
        if(road_index >= 0) {
            this.lastX = x;
            this.lastY = y;

            this.x = roadMade.get(road_index).getX();
            this.y = roadMade.get(road_index).getY();
            maze.remove(lastX, lastY);
            if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K' && symbolRemove.getSymbol() != 'C') {
                maze.setXY(lastX, lastY, symbolRemove);
            }
            symbolRemove = maze.getXY(x, y);
            this.maze.setXY(x, y, new Pinky());
        }
    }

    private int getOpositeDirection(int direction) {
        switch (direction){
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
        }
        return 0;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
