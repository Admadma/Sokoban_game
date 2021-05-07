package game.model;

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

    public int getRowChange() {
        return rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    /*
//erre nem biztos, hogy lesz szükség
    public static Direction of(int rowChange, int colChange) {  // of(1, -1) visszaadja az adott irányhoz tartozó nevet (ez DOWN_LEFT) (main method példa)        hogyha olyan irányt kapunk ami nincs a felsorolt 4 között
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        System.out.println(of(0, -1));
        //System.out.println(of(1, -2));
    }
    */
}
