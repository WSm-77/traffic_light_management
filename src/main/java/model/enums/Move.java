package model.enums;

public enum Move {
    RIGHT,
    STRAIGHT,
    LEFT;

    public static Move fromInteger(int value) throws IllegalArgumentException {
        Move[] moveValues = Move.values();
        if (value < 0 || value >= moveValues.length) {
            throw new IllegalArgumentException(String.format("Move can not be of value: %d", value));
        }

        return moveValues[value];
    }

    public static int count() {
        return Move.values().length;
    }
}
