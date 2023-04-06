package pt.isec.pa.tinypac.model.data;

public class EmptyZone implements IMazeElement{
    private final char symbol = ' ';

    @Override
    public char getSymbol() {
        return symbol;
    }
}