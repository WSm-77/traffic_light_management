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
    WEST_LEFT;

    public static Lane stringToLane(String start, String end) throws IllegalArgumentException {
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

            default -> throw new IllegalArgumentException(
                String.format("Couldn't parse startRoad=%s, endRoad=%s", start, end)
            );
        };
    }

    public Move getMove() {
        return Move.fromInteger(this.ordinal() % Move.count());
    }

    public Direction getDirection() {
        return Direction.fromInteger(this.ordinal() / Move.count());
    }

    public boolean collide(Lane other) {
        if (this.getDirection() == other.getDirection()) {
            return false;
        }
        else if (this.getDirection().are_opposite(other.getDirection())) {
            return (this.getMove() == Move.LEFT && other.getMove() != Move.LEFT) ||
                    (this.getMove() != Move.LEFT && other.getMove() == Move.LEFT);
        }
        else {
            Move previousLaneMove = this.getMove();
            Move nextLaneMove = other.getMove();

            if (this.getDirection().is_previous(other.getDirection())) {
                Move tmp = previousLaneMove;
                previousLaneMove = nextLaneMove;
                nextLaneMove = tmp;
            }

            return (nextLaneMove == Move.STRAIGHT) ||
                    (nextLaneMove == Move.LEFT && (previousLaneMove != Move.RIGHT));
        }
    }
}
