package pt.isec.pa.tinypac.model.data;

public class GhostSpot implements IMazeElement{
    private final char symbol = 'y';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
