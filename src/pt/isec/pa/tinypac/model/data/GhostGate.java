package pt.isec.pa.tinypac.model.data;

public class GhostGate implements IMazeElement{
    private final char symbol = 'Y';

    @Override
    public char getSymbol() {
        return symbol;
    }
}

