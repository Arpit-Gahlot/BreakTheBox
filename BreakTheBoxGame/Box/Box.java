package Box;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Box {

    GamePanel gp;
    public int boxHealthbar;
    public Color boxColor;
    public int xCoordinate;
    public int yCoordinate;

    public JLabel label = new JLabel();

    public Box(GamePanel gp) {
        this.gp = gp;
        yCoordinate = - gp.tileSize;

        randomizeXCoordinate();
        randomizeHealthBar();
        colorManager();
    }

    public void randomizeXCoordinate() {
        Random random = new Random();

        xCoordinate = (random.nextInt(14) + 1) * gp.tileSize;
    }

    public void randomizeHealthBar() {
        Random random = new Random();

        boxHealthbar = random.nextInt(3) + 1;
    }

    public void colorManager() {
        if (boxHealthbar == 1) {
            boxColor = Color.RED;
        }
        else if (boxHealthbar == 2) {
            boxColor = Color.YELLOW;
        }
        else if (boxHealthbar == 3) {
            boxColor = Color.GREEN;
        }
    }

    public void showHealthBar() {
        label.setText("" + boxHealthbar);
        label.setFont(new Font("MV Boli", Font.PLAIN, 5));
        label.setBounds(xCoordinate, yCoordinate, gp.tileSize, gp.tileSize);
        label.setForeground(Color.BLACK);
        gp.add(label);
    }
}