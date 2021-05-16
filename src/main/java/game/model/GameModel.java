package game.model;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;

import game.model.xmlhandler.*;
import org.tinylog.Logger;

public class GameModel {

    private boolean completed = false;

    private final Entity player;
    private final Entity[] walls;
    private final Entity[] balls;
    private final Entity[] goals;
    private int numberOfMoves;

    public GameModel(String loadingType){
        GameState gameState = null;
        try {
            gameState = XmlToJava.parseXml(loadingType);
        } catch (Exception e) {
            Logger.error("Failed to load game from XML");
            Platform.exit();
        }
        int[][] wallPositions = new int[gameState.getWallXValues().length][2];
        for(int i = 0; i < gameState.getWallXValues().length; i++){
            wallPositions[i][1] = gameState.getWallXValues()[i];
            wallPositions[i][0] = gameState.getWallYValues()[i];
        }
        this.walls = new Entity[wallPositions.length];
        for(int i = 0; i < wallPositions.length; i++){
            this.walls[i] = new Entity(EntityType.Wall, new Position(wallPositions[i][0], wallPositions[i][1]));
        }
        this.numberOfMoves = gameState.getNumberOfMoves();

        this.player = new Entity(EntityType.Player, new Position(gameState.getPlayerYValues()[0],gameState.getPlayerXValues()[0]));

        int[][] ballPositions = new int[gameState.getBallXValues().length][2];
        for(int i = 0; i < gameState.getBallXValues().length; i++){
            ballPositions[i][1] = gameState.getBallXValues()[i];
            ballPositions[i][0] = gameState.getBallYValues()[i];
        }
        this.balls = new Entity[ballPositions.length];
        for(int i = 0; i < ballPositions.length; i++){
            this.balls[i] = new Entity(EntityType.Ball, new Position(ballPositions[i][0], ballPositions[i][1]));
        }

        int[][] goalPositions = new int[gameState.getGoalXValues().length][2];
        for(int i = 0; i < gameState.getGoalXValues().length; i++){
            goalPositions[i][1] = gameState.getGoalXValues()[i];
            goalPositions[i][0] = gameState.getGoalYValues()[i];
        }
        this.goals = new Entity[goalPositions.length];
        for(int i = 0; i < goalPositions.length; i++){
            this.goals[i] = new Entity(EntityType.Goal, new Position(goalPositions[i][0], goalPositions[i][1]));
        }
    }

    public Position getPlayerPosition(){
        return player.getPosition();
    }

    public ObjectProperty<Position> playerPositionProperty(){
        return player.positionProperty();
    }
    public ObjectProperty<Position> ballPositionProperty(int index) {
        return balls[index].positionProperty();
    }

    public int getWallsLength(){
        return walls.length;
    }
    public int getBallsLength() {
        return balls.length;
    }
    public int getGoalsLength() {
        return goals.length;
    }

    public Position wallPosition(int index){
        return walls[index].getPosition();
    }
    public Position ballPosition(int index){
        return balls[index].getPosition();
    }
    public Position goalPosition(int index) {
        return goals[index].getPosition();
    }



    private boolean isValidMove(Direction direction, Position oldPosition){
        Position newPosition = oldPosition.moveTo(direction);
        for (var wall : walls){
            if(wall.getPosition().equals(newPosition)){
                return false;
            }
        }
        for (int i = 0; i < balls.length; i++){
            if(balls[i].getPosition().equals(newPosition)){
                if(!canPushBall(direction, i)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canPushBall(Direction direction, int index){
        var newBallPosition = balls[index].getPosition().moveTo(direction);
        for (var wall : walls){
            if(wall.getPosition().equals(newBallPosition)){
                return false;
            }
        }
        for (var ball : balls){
            if(ball.getPosition().equals(newBallPosition)){
                return false;
            }
        }
        ballPositionProperty(index).setValue(ballPosition(index).moveTo(direction));
        return true;
    }

    public void moveThere(Direction direction){
        if(isValidMove(direction, getPlayerPosition())) {
            playerPositionProperty().setValue(getPlayerPosition().moveTo(direction));
            numberOfMoves++;
            checkGoals();
        } else {
            Logger.warn("Can't move there");
        }
    }

    private void checkGoals(){
        if (!completed) {
            for (var goal : goals) {
                if (!ballOnIt(goal))
                    return;
            }
            completed = true;
            Logger.info("Game completed in {} moves.", numberOfMoves);
        }
    }

    private boolean ballOnIt(Entity goal){
        for(var ball : balls){
            if(goal.getPosition().equals(ball.getPosition()))
                return true;
        }
        return false;
    }

    public void saveGame(){
        try {
            JavaToXml.createXml(numberOfMoves, player, walls, balls, goals);
            Logger.info("Game saved");
        } catch (Exception e) {
            Logger.warn("Failed to save game");
        }

    }

    public void quitGame(){
        Logger.debug("Exiting game");
        Platform.exit();
    }

}
