package game;

import game.model.*;

import game.model.GameModel;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;


import static javafx.scene.paint.Color.*;

public class GameController {

    private GameModel model = new GameModel();

    private Position selected;
    private Player player;
    @FXML
    private GridPane board;


    @FXML
    private void initialize() {
        createBoard();
        createPlayer();
        createWalls();
        createBalls();
        createGoals();
        board.setOnKeyPressed(this::handleKeyPress);
    }

    private void createBoard() {
        for(int i = 0; i < 7; i++){
            for (int j = 0; j < 7; j++) {
                var tile = createTile();
                //tile.setOnKeyPressed(this::handleKeyPress);
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
        model.playerPositionProperty().addListener(this::entityPositionchange);
        //var player = new Circle(50).setFill(RED);
        var player = createPlayerHelp();
        //player.setFocusTraversable(true);
        //player.setOnKeyPressed(this::handleKeyPress);
        getTile(model.getPlayerPosition()).getChildren().add(player);

    }

    private Circle createPlayerHelp(){
        var player = new Circle(50);
        player.setFill(RED);
        //player.getStyleClass().add("player");
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
        return wall;
    }

    private void createBalls(){
        for (int i = 0; i < model.getBallsLength(); i++){
            model.ballPositionProperty(i).addListener(this::entityPositionchange);
            var ball = createBall();
            getTile(model.ballPosition(i)).getChildren().add(ball);
        }
    }

    private Circle createBall(){
        var ball = new Circle(50);
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
        goal.setWidth(100);
        goal.setHeight(100);
        goal.setFill(DARKGREY);
        return goal;
    }


    private void handleKeyPress(KeyEvent event) {
        System.out.println(event.getCode().getClass());
        //Logolni a gombot
        Position playerPosition = model.getPlayerPosition();
        System.out.println(playerPosition);
        whereToMove(event.getCode());
    }

    //a kapott betű alapján eldönti melyik irányba kell mozdulnia
    private void whereToMove(KeyCode key){
        switch (key) {

            case W:
            case UP:
                model.moveThere(Direction.UP);
                break;
            case D:
            case RIGHT:
                model.moveThere(Direction.RIGHT);
                break;
            case S:
            case DOWN:
                model.moveThere(Direction.DOWN);
                break;
            case A:
            case LEFT:
                model.moveThere(Direction.LEFT);
                break;
            default:
                System.out.println("Invalid key");
                break;

        }
    }


    private StackPane getTile(Position position){
        for(var child : board.getChildren()){
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()){
                return (StackPane) child;
            }
        }
        throw new AssertionError();

    }


    private void entityPositionchange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition){      //TODO: egyelőre sak a játékos pozícióját figyeljük, később lehet valahogy össze kell vonni a golyó figyelésével (mert nem éri meg külön külön figyelni)
        //Logger.debug("Move: {} -> {}", oldPosition, newPosition);                                                                     //TODO: problémát okozhat-e az hogy it csak golyó és square-ek voltak míg az enyémben lesz fal, player golyó,.. több különböző      //square(tile) elemen állhat player vagy golyó is
        StackPane oldTile = getTile(oldPosition);
        StackPane newTile = getTile(newPosition);
        //snewTile.getChildren().addAll(oldTile.getChildren());
        System.out.println("oldchilds: "+ oldTile.getChildren());
        System.out.println("newChilds" + newTile.getChildren());
        newTile.getChildren().add(oldTile.getChildren().get(oldTile.getChildren().size()-1));
        System.out.println("------ after");
        System.out.println("oldchilds: "+ oldTile.getChildren());
        System.out.println("newChilds" + newTile.getChildren());
        //oldTile.getChildren().clear();
        //System.out.println(newTile.lookup("Circle"));
/*
        newTile.getChildren().add(oldTile.getChildren().get(oldTile.getChildren().size()-1));
        if(oldTile.getChildren().size() > 1){
            oldTile.getChildren().remove(oldTile.getChildren().get(oldTile.getChildren().size()-1));
        }
*/

        System.out.println(newTile.getChildren());
        //newTile.getChildren().remove(newTile.getChildren().size()-1);
        System.out.println(newTile.getChildren());
        //newTile.getChildren().remove(newTile.lookup("Player"));
        //newTile.getChildren().add(observable.);
        //newTile.getChildren().remove(observable);

    }



}
