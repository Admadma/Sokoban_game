package model;

import game.model.Direction;
import game.model.GameModel;
import game.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    GameModel testModel = new GameModel("/TestGameState.xml", "testPlayer");

    @Test
    void testGetPlayerName(){
        assertEquals("testPlayer", testModel.getPlayerName());
    }
    @Test
    void testGetNumberOfMoves(){
        assertEquals(0, testModel.getNumberOfMoves());
    }
    @Test
    void testGetPlayerPosition(){
        assertEquals(new Position(1,1), testModel.getPlayerPosition());
    }

    @Test
    void testIsGameCompleteProperty(){
        testModel.isGameCompleteProperty().setValue(true);
        assertEquals(true, testModel.isGameCompleteProperty().get());
    }
    @Test
    void testPlayerPositionProperty(){
        testModel.playerPositionProperty().setValue(testModel.getPlayerPosition().moveTo(Direction.DOWN));
        assertEquals(new Position(2,1), testModel.playerPositionProperty().get());
    }

    @Test
    void testBallPositionProperty(){
        testModel.ballPositionProperty(0).setValue(new Position(4,4));
        testModel.ballPositionProperty(1).setValue(new Position(4,5));
        testModel.ballPositionProperty(2).setValue(testModel.ballPosition(2).moveTo(Direction.LEFT));
        assertEquals(new Position(4,4), testModel.ballPositionProperty(0).get());
        assertEquals(new Position(4,5), testModel.ballPositionProperty(1).get());
        assertEquals(new Position(2, 1), testModel.ballPositionProperty(2).get());
    }

    @Test
    void testWallsLength(){
        assertEquals(7, testModel.getWallsLength());
    }

    @Test
    void testBallsLength(){
        assertEquals(3, testModel.getBallsLength());
    }

    @Test
    void testGoalsLength(){
        assertEquals(3, testModel.getGoalsLength());
    }

    @Test
    void testWallPosition(){
        assertEquals(new Position(0,1), testModel.wallPosition(0));
        assertEquals(new Position(0,2), testModel.wallPosition(1));
        assertEquals(new Position(2,4), testModel.wallPosition(6));
    }

    @Test
    void testBallPosition(){
        assertEquals(new Position(1,2), testModel.ballPosition(0));
        assertEquals(new Position(1,3), testModel.ballPosition(1));
        assertEquals(new Position(2,2), testModel.ballPosition(2));
    }

    @Test
    void testGoalPosition(){
        assertEquals(new Position(1, 2), testModel.goalPosition(0));
        assertEquals(new Position(1, 3), testModel.goalPosition(1));
        assertEquals(new Position(2, 3), testModel.goalPosition(2));
    }

    @Test
    void testMoveThere(){
        Position startingPosition = testModel.getPlayerPosition();

        testModel.moveThere(Direction.LEFT);
        assertEquals(startingPosition, testModel.getPlayerPosition());

        testModel.moveThere(Direction.UP);
        assertEquals(startingPosition, testModel.getPlayerPosition());

        testModel.moveThere(Direction.RIGHT);
        assertEquals(startingPosition, testModel.getPlayerPosition());

        testModel.moveThere(Direction.DOWN);
        assertEquals(startingPosition.moveTo(Direction.DOWN), testModel.getPlayerPosition());

        testModel.moveThere(Direction.RIGHT);
        assertEquals(startingPosition.moveTo(Direction.DOWN).moveTo(Direction.RIGHT), testModel.getPlayerPosition());

        testModel.moveThere(Direction.RIGHT);
        assertNotEquals(startingPosition.moveTo(Direction.DOWN).moveTo(Direction.RIGHT).moveTo(Direction.RIGHT), testModel.getPlayerPosition());
    }

    @Test
    void testSaveGame(){
        testModel.saveGame();
    }

    @Test
    void testQuitGame(){
        testModel.quitGame();
    }
}
