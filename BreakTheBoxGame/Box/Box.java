/**
 * This class is used for representing all the boxes which are then handled by the boxManager
 */

package Box;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Box {

    //instance variables
    GamePanel gp;
    public int boxHealthbar;
    public Color boxColor;
    public int xCoordinate;
    public int yCoordinate;

    //constructor
    public Box(GamePanel gp) {
        this.gp = gp;
        yCoordinate = - gp.tileSize;

        randomizeXCoordinate();
        randomizeHealthBar();
        colorManager();
    }

    //Method to assign a random X coordinate to each box
    public void randomizeXCoordinate() {
        Random random = new Random();

        xCoordinate = (random.nextInt(14) + 1) * gp.tileSize;
    }

    //Method to assign a random healthbar to each box
    public void randomizeHealthBar() {
        Random random = new Random();

        boxHealthbar = random.nextInt(3) + 1;
    }

    //Method to assign a color to the box based on the healthbar
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
}
