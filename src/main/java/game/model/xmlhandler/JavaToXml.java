package game.model.xmlhandler;

import game.model.Entity;
import game.model.Position;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

import java.io.FileOutputStream;

public class JavaToXml {

    public static void createXml(int moves, Entity player, Entity[] walls, Entity[] balls, Entity[] goals) throws Exception{
        var gameState = new GameState();

        gameState.setNumberOfMoves(moves);

        int[] playerX = new int[]{player.getPosition().col()};
        gameState.setPlayerXValues(playerX);

        int[] playerY = new int[]{player.getPosition().row()};
        gameState.setPlayerYValues(playerY);

        int[] wallX = new int[walls.length];
        for(int i = 0; i < walls.length; i++){
            wallX[i] = walls[i].getPosition().col();
        }
        gameState.setWallXValues(wallX);

        int[] wallY = new int[walls.length];
        for(int i = 0; i < walls.length; i++){
            wallY[i] = walls[i].getPosition().row();
        }
        gameState.setWallYValues(wallY);

        int[] ballX = new int[balls.length];
        for(int i = 0; i < balls.length; i++){
            ballX[i] = balls[i].getPosition().col();
        }
        gameState.setBallXValues(ballX);

        int[] ballY = new int[balls.length];
        for(int i = 0; i < balls.length; i++){
            ballY[i] = balls[i].getPosition().row();
        }
        gameState.setBallYValues(ballY);

        int[] goalX = new int[goals.length];
        for(int i = 0; i < goals.length; i++){
            goalX[i] = goals[i].getPosition().col();
        }
        gameState.setGoalXValues(goalX);

        int[] goalY = new int[goals.length];
        for(int i = 0; i < goals.length; i++){
            goalY[i] = goals[i].getPosition().row();
        }
        gameState.setGoalYValues(goalY);

        JAXBContext jaxbContext = JAXBContext.newInstance(GameState.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(gameState, new FileOutputStream("gameOut.xml"));
    }
}
