package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void fromString_ValidValues() {
        assertEquals(Direction.NORTH, Direction.fromString("north"));
        assertEquals(Direction.EAST, Direction.fromString("east"));
        assertEquals(Direction.SOUTH, Direction.fromString("south"));
        assertEquals(Direction.WEST, Direction.fromString("west"));
    }

    @Test
    void fromString_InvalidValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Direction.fromString("invalid")
        );
    }

    @Test
    void fromInteger_ValidValues() {
        assertEquals(Direction.NORTH, Direction.fromInteger(0));
        assertEquals(Direction.EAST, Direction.fromInteger(1));
        assertEquals(Direction.SOUTH, Direction.fromInteger(2));
        assertEquals(Direction.WEST, Direction.fromInteger(3));
    }

    @Test
    void fromInteger_InvalidNegativeValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Direction.fromInteger(-1)
        );
    }

    @Test
    void fromInteger_InvalidLargeValue() {
        int invalidIndex = Direction.count();
        assertThrows(
                IllegalArgumentException.class,
                () -> Direction.fromInteger(invalidIndex)
        );
    }

    @Test
    void count() {
        assertEquals(4, Direction.count());
    }

    @Test
    void areOpposite() {
        assertTrue(Direction.NORTH.are_opposite(Direction.SOUTH));
        assertTrue(Direction.SOUTH.are_opposite(Direction.NORTH));
        assertTrue(Direction.EAST.are_opposite(Direction.WEST));
        assertTrue(Direction.WEST.are_opposite(Direction.EAST));

        assertFalse(Direction.NORTH.are_opposite(Direction.EAST));
        assertFalse(Direction.EAST.are_opposite(Direction.NORTH));
        assertFalse(Direction.EAST.are_opposite(Direction.SOUTH));
    }

    @Test
    void isPrevious() {
        assertTrue(Direction.NORTH.is_previous(Direction.WEST));
        assertTrue(Direction.EAST.is_previous(Direction.NORTH));
        assertTrue(Direction.SOUTH.is_previous(Direction.EAST));
        assertTrue(Direction.WEST.is_previous(Direction.SOUTH));

        assertFalse(Direction.NORTH.is_previous(Direction.EAST));
        assertFalse(Direction.WEST.is_previous(Direction.NORTH));
    }
}
