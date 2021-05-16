package model;

import game.model.Direction;
import game.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    Position testPosition = new Position(1,1);

    @Test
    void testRow(){
        assertEquals(1, testPosition.row());
        testPosition = testPosition.moveTo(Direction.UP);
        assertEquals(0, testPosition.row());
    }
    @Test
    void testCol(){
        assertEquals(1, testPosition.col());
        testPosition = testPosition.moveTo(Direction.RIGHT);
        assertEquals(2, testPosition.col());
    }
    @Test
    void testMoveTo(){
        assertEquals(new Position(0,1), testPosition.moveTo(Direction.UP));
        assertEquals(new Position(1,2), testPosition.moveTo(Direction.RIGHT));
        assertEquals(new Position(2,1), testPosition.moveTo(Direction.DOWN));
        assertEquals(new Position(1,0), testPosition.moveTo(Direction.LEFT));
    }

}
