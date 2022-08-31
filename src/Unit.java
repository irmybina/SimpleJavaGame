import java.util.Random;

public abstract class Unit {
    int unitX;
    int unitY;
    int destinationX = 20;
    int destinationY = 20;
    int count = 0;
    boolean isAlive = true;
    int randX = new Random().nextInt(20);
    int randY = new Random().nextInt(20);
    public abstract void movements();

    public void createCoin(){
        count++;
        while (GameField.fieldDots[randX][randY] != '_') {
            randX = new Random().nextInt(20);
            randY = new Random().nextInt(20);
        }
        GameField.fieldDots[randX][randY] = '*';
    }

    public void checkCollisions(){
        if (GameField.fieldDots[destinationX][destinationY] == '!'){
            GameField.fieldDots[unitX][unitY] = '_';
            isAlive = false;
        }
        if (GameField.fieldDots[destinationX][destinationY] == '*'){
            GameField.fieldDots[destinationX][destinationY] = '_';
            createCoin();
        }
    }
}