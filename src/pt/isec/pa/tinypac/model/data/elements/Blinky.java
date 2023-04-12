package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;

import java.util.Random;

public class Blinky extends Ghost {
    public Blinky(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move(MazeControl maze) {
        switch (direction) {
            case 1 -> { // right
               if (!maze.checkIfWall(x, y+1)) {
                    this.y++;
                    setY(this.y);
                }
               else {
                   setDirection(randomDirection());
                }
            }
            case 2 -> {
              if (!maze.checkIfWall(x, y-1)) {
                    this.y--;
                    setY(this.y);
                }
                else {
                    setDirection(randomDirection());
                }
            }
            case 3 -> {
                if (!maze.checkIfWall(x - 1, y) ) {
                    this.x--;
                    setX(this.x);
                }
                else {
                    setDirection(randomDirection());
                }
            }
            case 4 -> {
                if (!maze.checkIfWall(x + 1, y) )  {
                    this.x++;
                    setX(this.x);
                }
                else {
                    setDirection(randomDirection());
                }
            }
        }
    }

    public int randomDirection(){
        Random random = new Random();
        return random.nextInt(1,4);
    }
}
