package game.model;

/**
 * Enum representing the four cardinal directions.
 */
public enum Direction {
    UP(-1, 0),
    RIGHT(0,1),
    DOWN(1,0),
    LEFT(0,-1);


    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange){
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Returns the change in the y-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the y-coordinate when moving a step in this
     * direction
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Returns the change in the x-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the x-coordinate when moving a step in this
     * direction
     */
    public int getColChange() {
        return colChange;
    }
}
