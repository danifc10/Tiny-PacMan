package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;

public class Clyde extends Ghost {
    private final char symbol = 'C';
    public Clyde(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move(MazeControl maze) {
        System.out.println("ad3us");
    }


}
