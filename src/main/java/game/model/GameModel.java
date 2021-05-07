package game.model;

import javafx.beans.property.ObjectProperty;

public class GameModel {

    private final Player player;

    public GameModel(){
        this.player = new Player(new Position(1,1));
    }


    public Position getPlayerPosition(){
        return player.getPosition();
    }

    public ObjectProperty<Position> playerPositionProperty(){
        return player.positionProperty();
    }


    //TODO:getValidMoves   és/vagy isValidMove  implementációja Contorller canMoveThere helyett

    public boolean isValidMove(Direction direction, Position oldPosition){
        Position newPosition = oldPosition.moveTo(direction);
        //TODO: Player osztály helyett Entity osztály implementálása, player, ball, wall, goal entitás lesz, ezeken megy végig majd for each és vizsgálja hogy adott mezőn van-e valamelyik
        //for(entity : entities){if(entity.position=newPosition){if (ball...){}}..
        //azt nem kell vizsgálni, hogy a pályán van-e mivel fal fogja körbevenni a játékost, így nem is tudna a pályán kívülre kerülni

        return true;
    }

    public void moveThere(Direction direction){
        //System.out.println("here");
        if(isValidMove(direction, getPlayerPosition())) {
            playerPositionProperty().setValue(getPlayerPosition().moveTo(direction));
        }
    }

}
