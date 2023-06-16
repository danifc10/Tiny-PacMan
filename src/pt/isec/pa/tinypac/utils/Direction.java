package pt.isec.pa.tinypac.utils;
/**
 * enum with all possible directions
 * @author Daniela Correia
 * @version 1.0.0
 *
 */
public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    /**
     * Get the oposite direction
     * @param direction direction to get the oposite
     * @return oposite direction
     *
     */
    public static Direction opositeDirection(Direction direction) {
        switch (direction){
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }

        }
        return null;
    }
}
