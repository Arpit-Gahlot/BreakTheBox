package Turret;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CannonBall {

    public int xCoordinate;
    public int yCoordinate;
    public BufferedImage cannonBall;
    public int speed;

    public CannonBall(int xCoordinate, int yCoordinate) {

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        speed = 5;

        getCannonBallImage();
    }

    public void getCannonBallImage(){

        try {
            cannonBall = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/cannonBall.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        yCoordinate = yCoordinate - 5;
    }

    public void draw(Graphics g, GamePanel gp) {
        g.drawImage(cannonBall, xCoordinate, yCoordinate, gp.tileSize, gp.tileSize, null);
    }
}
