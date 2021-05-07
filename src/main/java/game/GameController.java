package game;

import game.model.*;

import game.model.GameModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


import javafx.scene.input.KeyEvent;


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
        board.setOnKeyPressed(this::handleKeyPress);
    }

    private void createBoard() {
        for(int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++) {
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

    private void createPlayer() {                                                   //kell a model osztályba egy    playerPositionProperty() method amihez adhatok majd egy listenert ami meghivja majd a mező frissítését
        model.playerPositionProperty().addListener(this::playerPositionChange); //ez elintézi a 3 paraméter adását (v, oldValue, newValue)
        //var player = new Circle(50).setFill(RED);
        var player = createPlayerHelp();
        //player.setFocusTraversable(true);
        //player.setOnKeyPressed(this::handleKeyPress);
        getTile(model.getPlayerPosition()).getChildren().add(player);

    }

    private Circle createPlayerHelp(){
        var player = new Circle(50);
        player.setFill(RED);
        return player;
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
            case W -> {
                model.moveThere(Direction.UP);
            }
            case D -> {
                model.moveThere(Direction.RIGHT);
            }
            case S -> {
                model.moveThere(Direction.DOWN);
            }
            case A -> {
                model.moveThere(Direction.LEFT);
            }
            default -> {
                System.out.println("Invalid input");
            }
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


    private void playerPositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition){      //TODO: egyelőre sak a játékos pozícióját figyeljük, később lehet valahogy össze kell vonni a golyó figyelésével (mert nem éri meg külön külön figyelni)
        //Logger.debug("Move: {} -> {}", oldPosition, newPosition);                                                                     //TODO: problémát okozhat-e az hogy it csak golyó és square-ek voltak míg az enyémben lesz fal, player golyó,.. több különböző      //square(tile) elemen állhat player vagy golyó is
        StackPane oldTile = getTile(oldPosition);
        StackPane newTile = getTile(newPosition);
        newTile.getChildren().addAll(oldTile.getChildren());
        oldTile.getChildren().clear();
    }



}
