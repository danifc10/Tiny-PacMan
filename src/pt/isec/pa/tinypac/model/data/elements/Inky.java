package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Inky extends Ghost implements IMazeElement {
    private final char symbol = 'I';
    public Inky(int x, int y, int direction, int speed) {
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
