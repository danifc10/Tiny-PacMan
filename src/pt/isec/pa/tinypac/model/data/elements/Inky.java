package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.*;

public class Inky extends Ghost  implements IMazeElement {
    public static final char symbol = 'I';
    IMazeElement symbolRemove = null;
    private int nextCornerX ;
    private int nextCornerY ;
    private int cornerIndex;
    private double distanceThreshold;
    int nextX, nextY;
    private int[][] corners; // coordenadas dos cantos do labirinto
    int[] dx = {-1, 1, 0, 0}; // Deslocamento na coordenada X para cada direção
    int[] dy = {0, 0, 1, -1}; // Deslocamento na coordenada Y para cada direção

    public Inky(){};

    public Inky(int x, int y, int direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
        this.direction = 2;
        this.corners = new int[][]{{maze.getXghostStart(),maze.getYghostStart()},{maze.getHeight(), maze.getWidth()},{maze.getHeight(), 0} ,{0, maze.getWidth()},  {0, 0}};
        this.cornerIndex = 0;
        this.distanceThreshold = maze.getHeight() * 0.15;
        this.nextCornerX = maze.getXghostStart();
        this.nextCornerY = maze.getYghostStart(); // Coordenada Y do próximo canto de destino
    }

    private double distanceToTarget(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void move() {
        int lastX = x;
        int lastY = y;
        /// verifica se o fantasma chegou ao canto pretendido
        if (x == (corners[cornerIndex][0]) && y == (corners[cornerIndex][1])) {
            if(cornerIndex == 0 && nextCornerX == maze.getXstart()){
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
        if (distanceToCorner <= distanceThreshold && nextCornerX != maze.getXghostStart()) {
            cornerIndex = (cornerIndex + 1) % corners.length; // avança para o próximo canto
            nextCornerX = corners[cornerIndex][0];
            nextCornerY = corners[cornerIndex][1];
        }

        if(cornerIndex == 0  && nextCornerX == maze.getXghostStart()){
            if(this.maze.getXY(x, y + 1).getSymbol() == 'Y'){
                direction = 2;
            }else if(this.maze.getXY(x, y -1 ).getSymbol() == 'Y'){
                direction = 3;
            }else if(this.maze.getXY(x - 1, y).getSymbol() == 'Y'){
                direction = 0;
            }else if(this.maze.getXY(x + 1, y).getSymbol() == 'Y'){
                direction = 1;
            }
        }

        // Verifica se pode seguir na direção atual
        int nextX = x + dx[direction];
        int nextY = y + dy[direction];

        if (!maze.checkIfWallGhost(nextX, nextY)) {
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

        maze.remove(lastX, lastY);
        if(symbolRemove != null && lastX != 0 && symbolRemove.getSymbol() != 'I' && symbolRemove.getSymbol() != 'B' && symbolRemove.getSymbol() != 'K') {
            maze.setXY(lastX, lastY, symbolRemove);
        }
        symbolRemove = maze.getXY(x, y);
        this.maze.setXY(x,y,new Inky());
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

    @Override
    public char getSymbol() {
        return symbol;
    }
}
