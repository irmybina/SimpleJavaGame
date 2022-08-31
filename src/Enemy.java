import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Enemy {
    public Image enemyImage;
    int enemyX;
    int enemyY;
    int enemyDestinationX = 20;
    int enemyDestinationY = 20;

    public Enemy(){
        loadImages();
    }

    private void loadImages(){
        ImageIcon iie = new ImageIcon("enemy.png");
        enemyImage = iie.getImage();
    }

    public void rotateEnemy(Graphics g, int x, int y, int d){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform reset = g2d.getTransform();
        if (d == 0){
            g2d.rotate(Math.toRadians(90), x+.5* GameField.DOT_SIZE, y+.5* GameField.DOT_SIZE);
            g.drawImage(enemyImage, x, y, null);
            enemyDestinationX = enemyX;
            enemyDestinationY = enemyY - 1;
        }
        else if (d == 1){
            g2d.rotate(Math.toRadians(180), x+.5* GameField.DOT_SIZE, y+.5* GameField.DOT_SIZE);
            g.drawImage(enemyImage, x, y, null);
            enemyDestinationX = enemyX - 1;
            enemyDestinationY = enemyY;
        }
        else if (d == 2){
            g2d.rotate(Math.toRadians(270), x+.5* GameField.DOT_SIZE, y+.5* GameField.DOT_SIZE);
            g.drawImage(enemyImage, x, y, null);
            enemyDestinationX = enemyX;
            enemyDestinationY = enemyY + 1;
        }
        else{
            g.drawImage(enemyImage, x, y, null);
            enemyDestinationX = enemyX + 1;
            enemyDestinationY = enemyY;
        }
        g2d.setTransform(reset);

    }

    private void checkCollisions(){
        if (GameField.fieldDots[enemyDestinationX][enemyDestinationY] == '1'){
            GameField.fieldDots[enemyDestinationX][enemyDestinationY] = '_';
            GameField.rook.isAlive = false;
        }
        if (GameField.fieldDots[enemyDestinationX][enemyDestinationY] == '2'){
            GameField.fieldDots[enemyDestinationX][enemyDestinationY] = '_';
            GameField.bishop.isAlive = false;
        }
    }

    public void moveEnemy(){
        if (enemyDestinationX>=0 && enemyDestinationX<=19 && enemyDestinationY>=0 && enemyDestinationY<=19 && GameField.fieldDots[enemyDestinationX][enemyDestinationY] != '0') {
            checkCollisions();
            GameField.fieldDots[enemyX][enemyY] = GameField.fieldDots[enemyDestinationX][enemyDestinationY];
            GameField.fieldDots[enemyDestinationX][enemyDestinationY] = '!';
            enemyDestinationX = 0;
            enemyDestinationY = 0;
        }
    }
}


