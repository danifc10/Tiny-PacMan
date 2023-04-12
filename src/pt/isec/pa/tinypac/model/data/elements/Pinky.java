package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;

public class Pinky extends Ghost {
    public Pinky(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move(MazeControl maze) {
        System.out.println("ok");
    }

}
