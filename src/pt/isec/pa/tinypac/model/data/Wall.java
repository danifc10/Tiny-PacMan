package pt.isec.pa.tinypac.model.data;

public class Wall implements IMazeElement{
    public static final char symbol = 'x';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
