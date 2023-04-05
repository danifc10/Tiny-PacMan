package pt.isec.pa.tinypac.model.data;

public class Point implements IMazeElement{
    private final char symbol = 'o';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
