package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.GhostDraw;
import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;

public class Blinky extends Ghost {
    public Blinky(int x, int y, Direction direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
    }

    @Override
    public boolean getOut(){
        int targetX = this.maze.getXghostStart();
        int targetY = this.maze.getYghostStart();
        while (x >= targetX) { // enquanto não sair da jaula
            this.lastX = x;
            this.lastY =y;
            // verifica se pode se mover na direção atual
            if (!this.maze.checkIfWall(x, y)) {
                // move na direção atual
                switch (direction) {
                    case LEFT:
                        y--;
                        break;
                    case UP:
                        x--;
                        break;
                    case RIGHT:
                        y++;
                        break;
                    case DOWN:
                        x++;
                        break;
                }
                check();
            } else {
                // não pode se mover na direção atual, sorteia uma nova direção
                Direction newDirection;
                do {
                    int randomIndex = (int) (Math.random() * availableDirections.size());
                    newDirection = availableDirections.get(randomIndex);
                } while (newDirection == direction);

            }
            //maze.removeGhostRoad(lastX, lastY);
            this.maze.remove(lastX, lastY);
            this.maze.setXY(x,y,new GhostDraw());
        }

        return true;
    }

    public void check(){
        if(this.maze.getXY(x, y - 1).getSymbol() == 'Y') {
            setDirection(Direction.LEFT);
        }
        else if(this.maze.getXY(x, y + 1).getSymbol() == 'Y') {
            setDirection(Direction.RIGHT);
        }
        else if(this.maze.getXY(x - 1, y).getSymbol() == 'Y') {
            setDirection(Direction.UP);
        }
        else if(this.maze.getXY(x + 1, y).getSymbol() == 'Y') {
            setDirection(Direction.DOWN);
        }
    }

    @Override
    public void move() {
        checkNeighbords(x, y);
        this.lastY = y;
        this.lastX = x;

        switch (direction) {
            case UP:
                if (this.maze.checkIfWall(x -1 ,y)){
                    // Escolhe uma nova direção aleatória dentre as disponíveis
                    int randomIndex = (int) (Math.random() * availableDirections.size());
                    direction = availableDirections.get(randomIndex);
                }else{
                    x -= speed;
                }
                break;

            case DOWN:
                if(this.maze.checkIfWall(x + 1 ,y)){
                    // Escolhe uma nova direção aleatória dentre as disponíveis
                    int randomIndex = (int) (Math.random() * availableDirections.size());
                    direction = availableDirections.get(randomIndex);
                }else{
                    x += speed;
                }
                break;
            case LEFT:
                if (this.maze.checkIfWall(x ,y - 1)){
                    // Escolhe uma nova direção aleatória dentre as disponíveis
                    int randomIndex = (int) (Math.random() * availableDirections.size());
                    direction = availableDirections.get(randomIndex);
                }else{
                    y -= speed;
                }

                break;
            case RIGHT:
                if (this.maze.checkIfWall(x ,y + 1)){
                    // Escolhe uma nova direção aleatória dentre as disponíveis
                    int randomIndex = (int) (Math.random() * availableDirections.size());
                    direction = availableDirections.get(randomIndex);
                }else{
                    y += speed;
                }
                break;
        }

        //maze.removeGhostRoad(lastX, lastY);
        this.maze.remove(lastX, lastY);
        this.maze.setXY(x,y,new GhostDraw());
    }

    private void checkNeighbords(int x, int y) {
        if(this.maze.getXY(x, y + 1).getSymbol() == 'P'){ //
            setDirection(Direction.RIGHT);
        }else if(this.maze.getXY(x, y -1 ).getSymbol() == 'P'){
            setDirection(Direction.LEFT);
        }else if(this.maze.getXY(x - 1, y).getSymbol() == 'P'){
            setDirection(Direction.UP);
        }else if(this.maze.getXY(x + 1, y).getSymbol() == 'P'){
            setDirection(Direction.DOWN);
        }
    }

    private Direction getOppositeDirection(Direction direction) {

        switch (direction){
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case DOWN:
                return Direction.UP;
            case UP:
                return Direction.DOWN;
        }

        return null;
    }


    public void setMaze(MazeControl maze){
        this.maze = maze;
    }
}
