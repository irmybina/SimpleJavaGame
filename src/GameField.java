import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    public static final int DOT_SIZE = 16;
    public static char[][] fieldDots = new char[20][20];

    private Image coin;
    private Image noGo;

    private boolean inGame = true;

    private Timer timer;

    static Rook rook = new Rook();
    static Bishop bishop = new Bishop();

    int enemyCount = 0;
    Enemy e0 = new Enemy();
    Enemy e1 = new Enemy();
    Enemy e2 = new Enemy();
    Enemy e3 = new Enemy();
    Enemy e4 = new Enemy();
    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    int d = new Random().nextInt(4);

    public GameField() {
        enemies.add(0, e0);
        enemies.add(1, e1);
        enemies.add(2, e2);
        enemies.add(3, e3);
        enemies.add(4, e4);
        setBackground(Color.black);
        loadImages();
        generateField();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    private void loadImages() {
        ImageIcon iic = new ImageIcon("coin.png");
        coin = iic.getImage();
        ImageIcon iis = new ImageIcon("noGo.png");
        noGo = iis.getImage();
    }

    private void generateField() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                fieldDots[i][j] = '_';
            }
        }

        int randX = new Random().nextInt(20);
        int randY = new Random().nextInt(20);

        //генерация препятствий
        for (int i = 0; i < 50; i++) {
            while (fieldDots[randX][randY] != '_') {
                randX = new Random().nextInt(20);
                randY = new Random().nextInt(20);
            }
            fieldDots[randX][randY] = '0';
        }

        //генерация монет
        for (int i = 0; i < 20; i++) {
            while (fieldDots[randX][randY] != '_') {
                randX = new Random().nextInt(20);
                randY = new Random().nextInt(20);
            }
            fieldDots[randX][randY] = '*';
        }

        //генерация врагов
        for (int i = 0; i < 5; i++) {
            while (fieldDots[randX][randY] != '_') {
                randX = new Random().nextInt(20);
                randY = new Random().nextInt(20);
            }
            fieldDots[randX][randY] = '!';
        }

        //место 1 юнита
        while (fieldDots[randX][randY] != '_') {
            randX = new Random().nextInt(20);
            randY = new Random().nextInt(20);
        }
        fieldDots[randX][randY] = '1';

        //место 2 юнита
        while (fieldDots[randX][randY] != '_') {
            randX = new Random().nextInt(20);
            randY = new Random().nextInt(20);
        }
        fieldDots[randX][randY] = '2';

        timer = new Timer(250,this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        enemyCount = 0;
        if (rook.isAlive || bishop.isAlive) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (fieldDots[j][i] == '0') {
                        g.drawImage(noGo, i * DOT_SIZE, j * DOT_SIZE, null);
                    }
                    if (fieldDots[j][i] == '*') {
                        g.drawImage(coin, i * DOT_SIZE, j * DOT_SIZE, null);
                    }
                    if (fieldDots[j][i] == '!') {
                        g.drawImage((enemies.get(enemyCount)).enemyImage, i*DOT_SIZE, j*DOT_SIZE, null);
                        (enemies.get(enemyCount)).enemyX = j;
                        (enemies.get(enemyCount)).enemyY = i;
                        (enemies.get(enemyCount)).rotateEnemy(g, i*DOT_SIZE, j*DOT_SIZE, d);
                        d = new Random().nextInt(4);
                        enemyCount += 1;
                    }
                    if (fieldDots[j][i] == '1' && rook.isAlive) {
                        g.drawImage(rook.rook_image, i * DOT_SIZE, j * DOT_SIZE, null);
                        rook.unitX = j;
                        rook.unitY = i;
                    }
                    if (fieldDots[j][i] == '2' && bishop.isAlive) {
                        g.drawImage(bishop.bishop_image, i * DOT_SIZE, j * DOT_SIZE, null);
                        bishop.unitX = j;
                        bishop.unitY = i;
                    }
                }
            }
            String countRook = "Rook count: " + Integer.toString(rook.count);
            g.setColor(Color.white);
            g.drawString(countRook, SIZE/2,350);

            String countBishop = "Bishop count: " + Integer.toString(bishop.count);
            g.setColor(Color.white);
            g.drawString(countBishop, 20,350);
            for (int i=0; i<5; i++){
                GameField.enemies.get(i).moveEnemy();
            }
        } else{
            inGame = false;
            String str1 = "Game Over";
            g.setColor(Color.white);
            g.drawString(str1, 125,SIZE/2);
            String str2 = "Total count: " + Integer.toString(rook.count + bishop.count);
            g.setColor(Color.white);
            g.drawString(str2, 125,SIZE/2 + 30);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            rook.movements();
            bishop.movements();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                rook.destinationX = rook.unitX;
                rook.destinationY = rook.unitY - 1;
            }
            if (key == KeyEvent.VK_UP) {
                rook.destinationX = rook.unitX - 1;
                rook.destinationY = rook.unitY;
            }
            if (key == KeyEvent.VK_RIGHT) {
                rook.destinationX = rook.unitX;
                rook.destinationY = rook.unitY + 1;
            }
            if (key == KeyEvent.VK_DOWN) {
                rook.destinationX = rook.unitX + 1;
                rook.destinationY = rook.unitY;
            }
            if (key == KeyEvent.VK_A) {
                bishop.destinationX = bishop.unitX + 1;
                bishop.destinationY = bishop.unitY - 1;
            }
            if (key == KeyEvent.VK_D) {
                bishop.destinationX = bishop.unitX + 1;
                bishop.destinationY = bishop.unitY + 1;
            }
            if (key == KeyEvent.VK_Q) {
                bishop.destinationX = bishop.unitX - 1;
                bishop.destinationY = bishop.unitY - 1;
            }
            if (key == KeyEvent.VK_E) {
                bishop.destinationX = bishop.unitX - 1;
                bishop.destinationY = bishop.unitY + 1;
            }
        }
    }
}
