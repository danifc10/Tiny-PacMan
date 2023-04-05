package pt.isec.pa.tinypac.model.data;

public class Wall implements IMazeElement{
    private final char symbol = 'x';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
