package pt.isec.pa.tinypac.utils;

public enum Direction {
    UP, DOWN, RIGHT, LEFT;

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
