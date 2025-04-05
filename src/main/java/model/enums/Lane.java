package model.enums;

public enum Lane {
    // NORTH
    NORTH_RIGHT,
    NORTH_STRAIGHT,
    NORTH_LEFT,

    // EAST
    EAST_RIGHT,
    EAST_STRAIGHT,
    EAST_LEFT,

    // SOUTH
    SOUTH_RIGHT,
    SOUTH_STRAIGHT,
    SOUTH_LEFT,

    // WEST
    WEST_RIGHT,
    WEST_STRAIGHT,
    WEST_LEFT,

    UNDEFINED;

    public static Lane stringToLane(String start, String end) {
        return switch (start + " " + end) {
            case "north west" -> NORTH_RIGHT;
            case "north south" -> NORTH_STRAIGHT;
            case "north east", "north north" -> NORTH_LEFT;

            case "east north" -> EAST_RIGHT;
            case "east west" -> EAST_STRAIGHT;
            case "east south", "east east" -> EAST_LEFT;

            case "south east" -> SOUTH_RIGHT;
            case "south north" -> SOUTH_STRAIGHT;
            case "south west", "south south" -> SOUTH_LEFT;

            case "west south" -> WEST_RIGHT;
            case "west east" -> WEST_STRAIGHT;
            case "west north", "west west" -> WEST_LEFT;

            default -> UNDEFINED;
        };
    }
}
