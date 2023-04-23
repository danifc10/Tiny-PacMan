package pt.isec.pa.tinypac.model.data;

public class EmptyZone implements IMazeElement{
    public static final char symbol = ' ';

    @Override
    public char getSymbol() {
        return symbol;
    }
}