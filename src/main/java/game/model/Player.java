package game.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Player {

    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Player(Position position){
        this.position.set(position);
    }

    public Position getPosition(){
        return position.get();
    }
    public ObjectProperty<Position> positionProperty() {
        return position;
    }
}
