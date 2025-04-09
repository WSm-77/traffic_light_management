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

    public static Lane stringToLane(String start, String end) {
        Direction startDirection = Direction.fromString(start);
        Direction endDirection = Direction.fromString(end);

        return switch (startDirection) {
            case NORTH -> switch(endDirection) {
                case WEST -> NORTH_RIGHT;
                case SOUTH -> NORTH_STRAIGHT;
                case EAST, NORTH -> NORTH_LEFT;
            };

            case EAST -> switch (endDirection) {
                case NORTH -> EAST_RIGHT;
                case WEST -> EAST_STRAIGHT;
                case SOUTH, EAST -> EAST_LEFT;
            };

            case SOUTH -> switch (endDirection) {
                case EAST -> SOUTH_RIGHT;
                case NORTH -> SOUTH_STRAIGHT;
                case WEST, SOUTH -> SOUTH_LEFT;
            };

            case WEST -> switch (endDirection) {
                case SOUTH -> WEST_RIGHT;
                case EAST -> WEST_STRAIGHT;
                case NORTH, WEST -> WEST_LEFT;
            };
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
