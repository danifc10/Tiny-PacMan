package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeControl;

public class Pinky extends Ghost implements IMazeElement {
    public static final char symbol = 'K';
    private int nextCornerX ;
    private int nextCornerY ;
    private int cornerIndex;
    private double distanceThreshold;
    private int[][] corners; // coordenadas dos cantos do labirinto
    int[] dx = {-1, 1, 0, 0}; // Deslocamento na coordenada X para cada direção
    int[] dy = {0, 0, 1, -1}; // Deslocamento na coordenada Y para cada direção

    public Pinky(int x, int y, int direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
        this.direction = 2;
        this.corners = new int[][]{{0, maze.getWidth() }, {maze.getWidth(), maze.getHeight()}, {0, 0},{maze.getWidth(), 0} };
        this.cornerIndex = 0;
        this.distanceThreshold = maze.getHeight() * 0.15;
        this.nextCornerX = corners[cornerIndex][0]; // Coordenada X do próximo canto de destino
        this.nextCornerY = corners[cornerIndex][1]; // Coordenada Y do próximo canto de destino
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

        if (!maze.checkIfWall(nextX, nextY) && maze.getXY(x, y).getSymbol() == 'Y') {
            x = nextX;
            y = nextY;
        } else {
            int minDist = Integer.MAX_VALUE;
            int bestDirection = -1;
            for (int d = 0; d < 4; d++) {
                int newX = x + dx[d];
                int newY = y + dy[d];

                if (!maze.checkIfWall(newX, newY)) {
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

        this.maze.removeGhostRoad(lastX, lastY);
        this.maze.setXY(x,y,new Pinky());
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
    public boolean getOut() {
        return false;
    }

    private void moveInDirection(int direction) {
        // Move o fantasma na direção definida
        switch (direction) {
            case 1: // 1 DOWN
                this.x++;
                break;
            case 2: // 2 RIGHT
                this.y++;
                break;
            case 0: // 0 UP
                this.x--;
                break;
            case 3: //3 LEFT
                this.y--;
                break;
        }
    }



    @Override
    public char getSymbol() {
        return symbol;
    }
}
