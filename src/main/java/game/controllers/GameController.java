package game.controllers;

import game.model.*;
import game.model.GameModel;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;


import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;
import org.tinylog.Logger;

import static javafx.scene.paint.Color.*;

public class GameController {

    private GameModel model;
    private String PlayerName = "";
    private Stage stage;

    @FXML
    private GridPane board;

    public void createGame(String loadingType, String playerName, Stage stage){
        this.stage = stage;
        try {
            this.model = new GameModel(loadingType, playerName);
            model.isGameCompleteProperty().addListener(this::isGameComplete);
            createBoard();
            createWalls();
            createGoals();
            createBalls();
            createPlayer();
            board.setOnKeyPressed(this::handleKeyPress);
            Logger.info("Created the game");
        } catch (Exception e){
            Logger.error(e.getMessage());
            Platform.exit();
        }
    }

    private void createBoard() {
        for(int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                var tile = createTile();
                board.add(tile, i, j);
            }
        }
    }

    private StackPane createTile(){
        var tile = new StackPane();
        tile.getStyleClass().add("tile");
        return tile;
    }

    private void createPlayer() {
        model.playerPositionProperty().addListener(this::entityPositionChange);
        var player = createPlayerHelp();
        getTile(model.getPlayerPosition()).getChildren().add(player);
    }

    private Circle createPlayerHelp(){
        var player = new Circle(40);
        player.setFill(RED);
        return player;
    }

    private void createWalls(){
        for (int i = 0; i < model.getWallsLength(); i++){
            Background background = new Background(new BackgroundFill(BLUE, CornerRadii.EMPTY, Insets.EMPTY));
            var wall = createWall(background);
            getTile(model.wallPosition(i)).getChildren().add(wall);
        }
    }

    private StackPane createWall(Background background){
        var wall = new StackPane();
        wall.setBackground(background);
        wall.getStyleClass().add("wall");
        return wall;
    }

    private void createBalls(){
        for (int i = 0; i < model.getBallsLength(); i++){
            model.ballPositionProperty(i).addListener(this::entityPositionChange);
            var ball = createBall();
            getTile(model.ballPosition(i)).getChildren().add(ball);
        }
    }

    private Circle createBall(){
        var ball = new Circle(40);
        ball.setFill(YELLOW);
        return ball;
    }

    private void createGoals(){
        for (int i = 0; i < model.getGoalsLength(); i++){
            var goal = createGoal();
            getTile(model.goalPosition(i)).getChildren().add(goal);
        }
    }

    private Rectangle createGoal(){
        var goal = new Rectangle();
        goal.setWidth(80);
        goal.setHeight(80);
        goal.setFill(DARKGREY);
        return goal;
    }


    private void handleKeyPress(KeyEvent event) {
        Logger.debug("Key pressed: {}", event.getCode());
        whereToMove(event.getCode());
    }

    private void whereToMove(KeyCode key){
        switch (key) {
            case UP:
                model.moveThere(Direction.UP);
                break;
            case RIGHT:
                model.moveThere(Direction.RIGHT);
                break;
            case DOWN:
                model.moveThere(Direction.DOWN);
                break;
            case LEFT:
                model.moveThere(Direction.LEFT);
                break;
            case S:
                model.saveGame();
                break;
            case Q:
                quitGame();
                break;
            default:
                Logger.warn("Invalid key");
                break;
        }
    }

    public void quitGame(){
        Logger.debug("Exiting game");
        Platform.exit();
    }

    private StackPane getTile(Position position){
        for(var child : board.getChildren()){
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()){
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void entityPositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition){
        Logger.debug("Moving entity from: ({}, {}) to ({}, {})", oldPosition.row(), oldPosition.col(), newPosition.row(), newPosition.col());
        StackPane oldTile = getTile(oldPosition);
        StackPane newTile = getTile(newPosition);
        newTile.getChildren().add(oldTile.getChildren().get(oldTile.getChildren().size()-1));
    }

    private void isGameComplete(ObservableValue<? extends Boolean> observable, boolean oldPosition, boolean newPosition){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/victoryScreenUI.fxml"));
            Parent root = fxmlLoader.load();
            VictoryScreenController controller = fxmlLoader.<VictoryScreenController>getController();
            controller.setPlayerName(model.getPlayerName());
            controller.setNumberOfMoves(model.getNumberOfMoves());

            Scene scene = new Scene(root);
            scene.getRoot().requestFocus();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e){
            Logger.error(e.getCause());
        }
    }



}

