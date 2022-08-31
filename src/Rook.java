import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Rook extends Unit{
    public Image rook_image;

    public Rook() {
        loadImages();
    }

    private void loadImages() {
        ImageIcon iii = new ImageIcon("unit_rook.png");
        rook_image = iii.getImage();
    }

    public void movements(){
        if (isAlive){
            if (destinationX>=0 && destinationX<=19 && destinationY>=0 && destinationY<=19 && GameField.fieldDots[destinationX][destinationY] != '0'){
                checkCollisions();
                GameField.fieldDots[unitX][unitY] = GameField.fieldDots[destinationX][destinationY];
                GameField.fieldDots[destinationX][destinationY] = '1';
            }
        }
    }
}
