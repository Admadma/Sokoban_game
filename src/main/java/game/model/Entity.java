package game.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Class for Entities used in the model.
 */
public class Entity {

    private final EntityType type;
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    /**
     * Creates a {@code Entity} object with the given parameters.
     *
     * @param type the type of this Entity object
     * @param position the position of this Entity object
     */
    public Entity(EntityType type, Position position){
        this.type = type;
        this.position.setValue(position);
    }

    /**
     * @return the position of this Entity object
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * @return a property to access the position of this Entity object
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }
}
