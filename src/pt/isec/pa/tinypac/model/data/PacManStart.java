package pt.isec.pa.tinypac.model.data;

public class PacManStart implements IMazeElement{
    private final char symbol = 'M';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
