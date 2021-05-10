package game.model;

import javafx.beans.property.ObjectProperty;

public class GameModel {

    private final Entity player;
    private final Entity[] walls;
    private final Entity[] balls;
    private final Entity[] goals;

    public GameModel(){
        this.player = new Entity(EntityType.Player, new Position(1,1));
        this.walls = new Entity[] {
                //new Entity(EntityType.Wall, new Position(2,2)),
                new Entity(EntityType.Wall, new Position(2,3)),
                //ew Entity(EntityType.Wall, new Position(2,4)),
                new Entity(EntityType.Wall, new Position(3,0)),
                new Entity(EntityType.Wall, new Position(3,1))
        };
        this.balls = new Entity[] {
                new Entity(EntityType.Ball, new Position(2,2)),
                new Entity(EntityType.Ball, new Position(1,2))
        };
        this.goals = new Entity[] {
                new Entity(EntityType.Goal, new Position(4,4)),
                new Entity(EntityType.Goal, new Position(4,5))
        };

    }


    public Position getPlayerPosition(){
        return player.getPosition();
    }
    public Position getBallPosition(int index){
        return balls[index].getPosition();
    }

    public ObjectProperty<Position> playerPositionProperty(){
        return player.positionProperty();
    }
    public ObjectProperty<Position> ballPositionProperty(int index) {
        return balls[index].positionProperty();
    }

    public int getWallsLength(){
        return walls.length;
    }
    public int getBallsLength() {
        return balls.length;
    }
    public int getGoalsLength() {
        return goals.length;
    }

    public Entity[] getWalls() {
        return walls;
    }

    public Position wallPosition(int index){
        return walls[index].getPosition();
    }
    public Position ballPosition(int index){
        return balls[index].getPosition();
    }
    public Position goalPosition(int index) {
        return goals[index].getPosition();
    }



    public boolean isValidMove(Direction direction, Position oldPosition){
        Position newPosition = oldPosition.moveTo(direction);
        //azt nem kell vizsgálni, hogy a pályán van-e mivel fal fogja körbevenni a játékost, így nem is tudna a pályán kívülre kerülni

        for (var wall : walls){
            if(wall.getPosition().equals(newPosition)){
                return false;
            }
        }
        for (int i = 0; i < balls.length; i++){
            if(balls[i].getPosition().equals(newPosition)){
                if(!canPushBall(direction, i)){
                    return false;
                }

            }
        }
        //TODO: vizsgálni, hogy Goal meőre lépek-e mivel akkor az entityPositionChange() törölni fogja ha lelépek róla
        return true;
    }

    private boolean canPushBall(Direction direction, int index){
        var newBallPosition = balls[index].getPosition().moveTo(direction);
        for (var wall : walls){
            if(wall.getPosition().equals(newBallPosition)){
                return false;
            }
        }
        for (var ball : balls){
            if(ball.getPosition().equals(newBallPosition)){
                return false;
            }
        }
        ballPositionProperty(index).setValue(getBallPosition(index).moveTo(direction));
        return true;
    }

    public void moveThere(Direction direction){
        //System.out.println("here");
        if(isValidMove(direction, getPlayerPosition())) {
            playerPositionProperty().setValue(getPlayerPosition().moveTo(direction));
            checkGoals();
        }
        //TODO: vizsgálni hogy célba értek-e a golyók

    }

    private void checkGoals(){
        for(var goal : goals){
            if(!ballOnIt(goal))
                return;
        }
        System.out.println("complete");
    }

    private boolean ballOnIt(Entity goal){
        for(var ball : balls){
            if(goal.getPosition().equals(ball.getPosition()))
                return true;
        }
        return false;
    }



}
