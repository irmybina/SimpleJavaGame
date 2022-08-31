import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bishop extends Unit{
    public Image bishop_image;

    public Bishop() {
        loadImages();
    }

    private void loadImages() {
        ImageIcon iii = new ImageIcon("unit_bishop.png");
        bishop_image = iii.getImage();
    }

    public void movements() {
        if (isAlive){
            if (destinationX>=0 && destinationX<=19 && destinationY>=0 && destinationY<=19 && GameField.fieldDots[destinationX][destinationY] != '0'){
                checkCollisions();
                GameField.fieldDots[unitX][unitY] = GameField.fieldDots[destinationX][destinationY];
                GameField.fieldDots[destinationX][destinationY] = '2';
            }
        }
    }

}
