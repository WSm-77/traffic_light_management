package model.enums;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction fromInteger(int value) throws IllegalArgumentException {
        Direction[] directionValues = Direction.values();
        if (value < 0 || value >= directionValues.length) {
            throw new IllegalArgumentException(String.format("Direction can not be of value: %d", value));
        }

        return directionValues[value];
    }

    public static int count() {
        return Direction.values().length;
    }

    public boolean are_opposite(Direction other) {
        return (this.ordinal() + 2) % Direction.count() == other.ordinal();
    }

    public boolean is_previous(Direction other) {
        return (other.ordinal() + 1) % Direction.count() == this.ordinal();
    }
}
