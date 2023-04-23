package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.MazeControl;

public class Clyde extends Ghost {
    private final char symbol = 'C';
    public Clyde(int x, int y, int direction, int speed, MazeControl maze) {
        super(x, y, direction, speed, maze);
    }

    @Override
    public void move() {

    }
    @Override
    public boolean getOut() {
        return false;
    }


}
