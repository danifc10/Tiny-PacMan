package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;

public class Inky extends Ghost  {
    public Inky(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move(MazeControl maze) {
        System.out.println();
    }

}
