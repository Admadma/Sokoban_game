package game.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Class declaring default methods of used entities.
 */
public class Entity {

    private final EntityType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Entity(EntityType type, Position position){
        this.type = type;
        this.position.setValue(position);
    }

    public EntityType getType() {
        return type;
    }

    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }
}
