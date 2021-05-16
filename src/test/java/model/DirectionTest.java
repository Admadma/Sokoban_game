package model;

import game.model.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void testGetRowChange(){
        assertEquals(-1, Direction.UP.getRowChange());
        assertEquals(0, Direction.RIGHT.getRowChange());
        assertEquals(1, Direction.DOWN.getRowChange());
        assertEquals(0, Direction.LEFT.getRowChange());
    }

    @Test
    void testGetColChange(){
        assertEquals(0, Direction.UP.getColChange());
        assertEquals(1, Direction.RIGHT.getColChange());
        assertEquals(0, Direction.DOWN.getColChange());
        assertEquals(-1, Direction.LEFT.getColChange());
    }

}
