package model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    @Test
    void fromInteger_ValidValues() {
        assertEquals(Move.RIGHT, Move.fromInteger(0));
        assertEquals(Move.STRAIGHT, Move.fromInteger(1));
        assertEquals(Move.LEFT, Move.fromInteger(2));
    }

    @Test
    void fromInteger_InvalidNegativeValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Move.fromInteger(-1)
        );
    }

    @Test
    void fromInteger_InvalidLargeValue() {
        int invalidIndex = Move.count();
        assertThrows(
                IllegalArgumentException.class,
                () -> Move.fromInteger(invalidIndex)
        );
    }

    @Test
    void count() {
        assertEquals(3, Move.count());
    }
}
