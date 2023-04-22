package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;
import pt.isec.pa.tinypac.utils.Direction;

public class Inky extends Ghost  {
    public Inky(int x, int y, Direction direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
    }

    @Override
    public void move() {
        System.out.println("ola3");
    }

    @Override
    public boolean getOut() {
        return false;
    }

}
