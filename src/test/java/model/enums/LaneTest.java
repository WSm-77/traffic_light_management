package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaneTest {

    @Test
    void stringToLane_ValidValues() {
        assertEquals(Lane.NORTH_RIGHT, Lane.stringToLane("north", "west"));
        assertEquals(Lane.NORTH_STRAIGHT, Lane.stringToLane("north", "south"));
        assertEquals(Lane.NORTH_LEFT, Lane.stringToLane("north", "east"));

        assertEquals(Lane.EAST_RIGHT, Lane.stringToLane("east", "north"));
        assertEquals(Lane.EAST_STRAIGHT, Lane.stringToLane("east", "west"));
        assertEquals(Lane.EAST_LEFT, Lane.stringToLane("east", "south"));

        assertEquals(Lane.SOUTH_RIGHT, Lane.stringToLane("south", "east"));
        assertEquals(Lane.SOUTH_STRAIGHT, Lane.stringToLane("south", "north"));
        assertEquals(Lane.SOUTH_LEFT, Lane.stringToLane("south", "west"));

        assertEquals(Lane.WEST_RIGHT, Lane.stringToLane("west", "south"));
        assertEquals(Lane.WEST_STRAIGHT, Lane.stringToLane("west", "east"));
        assertEquals(Lane.WEST_LEFT, Lane.stringToLane("west", "north"));
    }

    @Test
    void stringToLane_InvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> Lane.stringToLane("north", "invalid"));
        assertThrows(IllegalArgumentException.class, () -> Lane.stringToLane("invalid", "west"));
    }

    @Test
    void getMove_ValidValues() {
        assertEquals(Move.RIGHT, Lane.NORTH_RIGHT.getMove());
        assertEquals(Move.STRAIGHT, Lane.NORTH_STRAIGHT.getMove());
        assertEquals(Move.LEFT, Lane.NORTH_LEFT.getMove());

        assertEquals(Move.RIGHT, Lane.EAST_RIGHT.getMove());
        assertEquals(Move.STRAIGHT, Lane.EAST_STRAIGHT.getMove());
        assertEquals(Move.LEFT, Lane.EAST_LEFT.getMove());

        assertEquals(Move.RIGHT, Lane.SOUTH_RIGHT.getMove());
        assertEquals(Move.STRAIGHT, Lane.SOUTH_STRAIGHT.getMove());
        assertEquals(Move.LEFT, Lane.SOUTH_LEFT.getMove());

        assertEquals(Move.RIGHT, Lane.WEST_RIGHT.getMove());
        assertEquals(Move.STRAIGHT, Lane.WEST_STRAIGHT.getMove());
        assertEquals(Move.LEFT, Lane.WEST_LEFT.getMove());
    }

    @Test
    void getDirection_ValidValues() {
        assertEquals(Direction.NORTH, Lane.NORTH_RIGHT.getDirection());
        assertEquals(Direction.NORTH, Lane.NORTH_STRAIGHT.getDirection());
        assertEquals(Direction.NORTH, Lane.NORTH_LEFT.getDirection());

        assertEquals(Direction.EAST, Lane.EAST_STRAIGHT.getDirection());
        assertEquals(Direction.SOUTH, Lane.SOUTH_LEFT.getDirection());
        assertEquals(Direction.WEST, Lane.WEST_RIGHT.getDirection());
    }

    @Test
    void collide_OppositeDirections() {
        // turn right
        assertTrue(Lane.NORTH_LEFT.collide(Lane.SOUTH_RIGHT));
        assertTrue(Lane.SOUTH_RIGHT.collide(Lane.NORTH_LEFT));
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.NORTH_RIGHT));
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.NORTH_STRAIGHT));

        // turn left
        assertTrue(Lane.SOUTH_LEFT.collide(Lane.NORTH_STRAIGHT));
        assertFalse(Lane.SOUTH_LEFT.collide(Lane.NORTH_LEFT));

        // go straight
        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.NORTH_LEFT));
        assertFalse(Lane.SOUTH_STRAIGHT.collide(Lane.NORTH_STRAIGHT));
        assertFalse(Lane.SOUTH_STRAIGHT.collide(Lane.NORTH_RIGHT));
    }

    @Test
    void collide_AdjacentDirections() {
        // turn right
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.EAST_LEFT));
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.EAST_RIGHT));
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.EAST_STRAIGHT));

        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.WEST_LEFT));
        assertFalse(Lane.SOUTH_RIGHT.collide(Lane.WEST_RIGHT));
        assertTrue(Lane.SOUTH_RIGHT.collide(Lane.WEST_STRAIGHT));

        // turn left
        assertTrue(Lane.SOUTH_LEFT.collide(Lane.EAST_LEFT));
        assertFalse(Lane.SOUTH_LEFT.collide(Lane.EAST_RIGHT));
        assertTrue(Lane.SOUTH_LEFT.collide(Lane.EAST_STRAIGHT));

        assertTrue(Lane.SOUTH_LEFT.collide(Lane.WEST_LEFT));
        assertFalse(Lane.SOUTH_LEFT.collide(Lane.WEST_RIGHT));
        assertTrue(Lane.SOUTH_LEFT.collide(Lane.WEST_STRAIGHT));

        // go straight
        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.EAST_LEFT));
        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.EAST_RIGHT));
        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.EAST_STRAIGHT));

        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.WEST_LEFT));
        assertFalse(Lane.SOUTH_STRAIGHT.collide(Lane.WEST_RIGHT));
        assertTrue(Lane.SOUTH_STRAIGHT.collide(Lane.WEST_STRAIGHT));

    }
}
