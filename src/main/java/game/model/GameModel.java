package game.model;

import javafx.application.Platform;
import javafx.beans.property.*;

import game.model.xmlhandler.*;
import org.tinylog.Logger;

/**
 * Class representing the state of Entities on the board.
 */
public class GameModel {

    private boolean completed = false;
    private BooleanProperty isGameComplete = new SimpleBooleanProperty(false);

    private final Entity player;
    private final Entity[] walls;
    private final Entity[] balls;
    private final Entity[] goals;

    private int numberOfMoves;
    private String playerName;

    /**
     * Creates a GameModel object with entities from the sourcefile.
     *
     * @param sourceFile XML file containing x and y parameters for all the entities on the game board
     * @param playerName when starting a new game users can enter their names.
     */
    public GameModel(String sourceFile, String playerName){

        GameState gameState = null;
        try {
            gameState = XmlToJava.parseXml(sourceFile);
        } catch (Exception e) {
            Logger.error("Failed to load game from XML");
            Platform.exit();
        }
        if(playerName.equals("")){
            this.playerName = gameState.getPlayerName();
        } else {
            this.playerName = playerName;
        }

        this.numberOfMoves = gameState.getNumberOfMoves();

        this.player = new Entity(EntityType.Player, new Position(gameState.getPlayerYValues()[0],gameState.getPlayerXValues()[0]));

        int[][] wallPositions = new int[gameState.getWallXValues().length][2];
        for(int i = 0; i < gameState.getWallXValues().length; i++){
            wallPositions[i][1] = gameState.getWallXValues()[i];
            wallPositions[i][0] = gameState.getWallYValues()[i];
        }
        this.walls = new Entity[wallPositions.length];
        for(int i = 0; i < wallPositions.length; i++){
            this.walls[i] = new Entity(EntityType.Wall, new Position(wallPositions[i][0], wallPositions[i][1]));
        }

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

    /**
     * @return the name of the player, which was either entered at start or loaded from savefile
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * @return the number of valid moves the player took this game
     */
    public int getNumberOfMoves(){
        return numberOfMoves;
    }
    /**
     * @return the position of the player Entity
     */
    public Position getPlayerPosition(){
        return player.getPosition();
    }

    /**
     * @return a property that tells whether the game is completed or not
     */
    public BooleanProperty isGameCompleteProperty() {
        return isGameComplete;
    }

    /**
     * @return a property to access the position of the player
     */


    public ObjectProperty<Position> playerPositionProperty(){
        return player.positionProperty();
    }
    /**
     * @param index of the sought ball Entity
     * @return a property to access the position of given ball Entity
     */
    public ObjectProperty<Position> ballPositionProperty(int index) {
        return balls[index].positionProperty();
    }

    /**
     * @return the length of the walls array
     */
    public int getWallsLength(){
        return walls.length;
    }
    /**
     * @return the length of the balls array
     */
    public int getBallsLength() {
        return balls.length;
    }
    /**
     * @return the length of the goals array
     */
    public int getGoalsLength() {
        return goals.length;
    }

    /**
     * @param index of the sought wall Entity
     * @return the position of given wall Entity
     */
    public Position wallPosition(int index){
        return walls[index].getPosition();
    }
    /**
     * @param index of the sought ball Entity
     * @return the position of given ball Entity
     */
    public Position ballPosition(int index){
        return balls[index].getPosition();
    }
    /**
     * @param index of the sought goal Entity
     * @return the position of given goal Entity
     */
    public Position goalPosition(int index) {
        return goals[index].getPosition();
    }


    /**
     * Checks if the Entity on given position can move in given direction.
     *
     * @param direction in which it tries to move
     * @param oldPosition the position from where it tries to move
     * @return returns true if the move is possible, false if not
     */
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

    /**
     * Method to attempt moving the player in given direction.
     *
     * @param direction in which it tries to move
     */
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
        if (!isGameComplete.get()) {
            for (var goal : goals) {
                if (!ballOnIt(goal))
                    return;
            }
            isGameComplete.set(true);
            Logger.info("{} completed the game in {} moves.", playerName, numberOfMoves);
        }
    }
/*
    private void checkGoals(){
        if (!completed) {
            for (var goal : goals) {
                if (!ballOnIt(goal))
                    return;
            }
            completed = true;
            Logger.info("{} completed the game in {} moves.", playerName, numberOfMoves);
        }
    }*/

    private boolean ballOnIt(Entity goal){
        for(var ball : balls){
            if(goal.getPosition().equals(ball.getPosition()))
                return true;
        }
        return false;
    }

    /**
     * Attempts to save the game using the {@code JavaToXml} class.
     */
    public void saveGame(){
        try {
            JavaToXml.createXml(numberOfMoves, playerName, player, walls, balls, goals);
            Logger.info("Game saved");
        } catch (Exception e) {
            Logger.warn("Failed to save game");
        }

    }

    /**
     * Exits the game.
     */
    public void quitGame(){
        Logger.debug("Exiting game");
        Platform.exit();
    }

}
