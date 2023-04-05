package pt.isec.pa.tinypac.model.data;

public class Fruit implements IMazeElement{
    private final char symbol = 'F';

    @Override
    public char getSymbol() {
        return symbol;
    }
}
