package game.model;

/**
 * Record class representig an y and x coordinate pair.
 *
 * @param row the y coordinate
 * @param col the x coordinate
 */
public record Position(int row, int col) {
    /**
     * Method calculating the new position after moving in given direction.
     * @param direction the direction to which the entity is moved
     * @return the new position
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }
}