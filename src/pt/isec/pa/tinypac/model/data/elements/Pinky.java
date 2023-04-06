package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Pinky extends Ghost implements IMazeElement {
    private final char symbol = 'p';
    public Pinky(int x, int y, int direction, int speed) {
        super(x, y, direction, speed);
    }

    @Override
    public void move() {

    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
