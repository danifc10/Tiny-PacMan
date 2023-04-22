package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;

public class Pinky extends Ghost {
    public Pinky(int x, int y, Direction direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
    }

    @Override

    public void move() {
        // Move o fantasma na direção definida
        switch (this.direction) {
            case DOWN:
                this.x++;
                break;
            case RIGHT:
                this.y++;
                break;
            case UP:
                this.x--;
                break;
            case LEFT:
                this.y--;
                break;
        }
    }


    @Override
    public boolean getOut() {
        move();
        return false;
    }
}
