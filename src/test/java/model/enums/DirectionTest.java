package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testFromInteger_ValidValues() {
        assertEquals(Direction.NORTH, Direction.fromInteger(0));
        assertEquals(Direction.EAST, Direction.fromInteger(1));
        assertEquals(Direction.SOUTH, Direction.fromInteger(2));
        assertEquals(Direction.WEST, Direction.fromInteger(3));
    }

    @Test
    void testFromInteger_InvalidNegativeValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Direction.fromInteger(-1)
        );
    }

    @Test
    void testFromInteger_InvalidLargeValue() {
        int invalidIndex = Direction.count();
        assertThrows(
                IllegalArgumentException.class,
                () -> Direction.fromInteger(invalidIndex)
        );
    }

    @Test
    void testCount() {
        assertEquals(4, Direction.count());
    }

    @Test
    void testAreOpposite() {
        assertTrue(Direction.NORTH.are_opposite(Direction.SOUTH));
        assertTrue(Direction.SOUTH.are_opposite(Direction.NORTH));
        assertTrue(Direction.EAST.are_opposite(Direction.WEST));
        assertTrue(Direction.WEST.are_opposite(Direction.EAST));

        assertFalse(Direction.NORTH.are_opposite(Direction.EAST));
        assertFalse(Direction.EAST.are_opposite(Direction.NORTH));
        assertFalse(Direction.EAST.are_opposite(Direction.SOUTH));
    }

    @Test
    void testIsPrevious() {
        assertTrue(Direction.NORTH.is_previous(Direction.WEST));
        assertTrue(Direction.EAST.is_previous(Direction.NORTH));
        assertTrue(Direction.SOUTH.is_previous(Direction.EAST));
        assertTrue(Direction.WEST.is_previous(Direction.SOUTH));

        assertFalse(Direction.NORTH.is_previous(Direction.EAST));
        assertFalse(Direction.WEST.is_previous(Direction.NORTH));
    }
}
