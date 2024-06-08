/**
 * This class handles the working of the cannonBalls which are launched from the turret
 */

package Turret;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CannonBall {

    //instance variables
    public int xCoordinate;
    public int yCoordinate;
    public BufferedImage cannonBall;
    public int speed;

    //constructor
    public CannonBall(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        speed = 5;

        getCannonBallImage();
    }

    //Method to assign the cannonBall image to the cannonBall variable
    public void getCannonBallImage(){

        try {
            cannonBall = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/cannonBall.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Method to make the cannonBalls move up
    public void update() {
        yCoordinate = yCoordinate - 5;
    }

    //Method to draw the cannonBall
    public void draw(Graphics g, GamePanel gp) {
        g.drawImage(cannonBall, xCoordinate, yCoordinate, gp.tileSize, gp.tileSize, null);
    }
}
