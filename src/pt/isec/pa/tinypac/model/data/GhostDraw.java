package pt.isec.pa.tinypac.model.data;

public class GhostDraw implements IMazeElement{
    private final char symbol = 'G';
    @Override
    public char getSymbol() {
        return symbol;
    }
}
