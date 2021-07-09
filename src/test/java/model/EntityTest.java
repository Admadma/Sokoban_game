package model;

import game.model.Direction;
import game.model.Entity;
import game.model.EntityType;
import game.model.Position;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    Position testPosition = new Position(1,1);
    Entity testPlayerEntity = new Entity(EntityType.PLAYER, testPosition);

    @Test
    void testGetPosition(){
        assertEquals(new Position(1, 1), testPlayerEntity.getPosition());
        assertNotEquals(new Position(0, 0), testPlayerEntity.getPosition());
    }

    @Test
    void testPositionProperty(){
        testPlayerEntity.positionProperty().setValue(testPosition.moveTo(Direction.UP));
        assertEquals(new Position(0,1), testPlayerEntity.getPosition());
    }
}
