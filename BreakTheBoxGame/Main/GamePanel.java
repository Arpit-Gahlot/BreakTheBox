/**
 * This class contains all the details of the Game screen and other basic game settings
 * IMPORTANT: THIS CLASS WAS CREATED WITH THE HELP FROM @RYISNOW ON YOUTUBE.
 */

package Main;

import Turret.Turret;
import Box.BoxManager;
//import ObstacleDetails.Obstacles;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTileSize = 16; //16x16 tile

    //We need to increase the scale as modern computers have very high resolutions
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48 tile (this will be actually displayed)

    //These variables determine the size of the screen (window)
    public final int maxScreenColumn = 16; //the number of columns on the screen
    public final int maxScreenRow = 12; //the number of rows on the screen
    public final int screenWidth = tileSize * maxScreenColumn; //the actual width of the screen (768 pixels)
    public final int screenHeight = tileSize * maxScreenRow; //the actual height of the screen (576 pixels)

    public int gameTimer = 0;

    //The screen will be updated 60 times every second
    int FPS = 60;

    //Add the key handler
    KeyHandler keyH = new KeyHandler();

    //Time is an integral component of every game. Thread allows us to start and stop a game.
    Thread gameThread;

    //instantiate the Turret class
    public Turret turret = new Turret(this, keyH);

    //instantiate the BoxManager class
    public BoxManager boxManager = new BoxManager(this);

    //instantiate the collision checker class
    //public CollisionChecker cChecker = new CollisionChecker(this);

    //Constructor
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of the screen
        this.setDoubleBuffered(true); //All the drawing will be done in an off-screen paint buffer (higher performance)
        this.addKeyListener(keyH); //Add the key handler to the Game panel
        this.setFocusable(true); //Game Panel can be focused to receive key input
        this.setBackground(Color.WHITE);
    }

    //starts the game events/sequence
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * This method does the following every time it is run
     * 1. Update - character positions
     * 2. Draw - tiles and other objects
     * 3. etc.
     */
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            //check current time
            currentTime = System.nanoTime();
            delta = delta + ((currentTime - lastTime) / drawInterval);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                gameTimer++;
            }
        }
    }

    //update information such as player positions
    public void update() {

        turret.update();
        boxManager.update();

    }

    //draw screen with the updated information
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        //Graphics2D extends the Graphics class and provides more coordinate control, etc compared to Graphics.
        Graphics2D g2 = (Graphics2D) g;

        turret.draw(g2);

        boxManager.draw(g2);

        //dispose of this graphics context and release any system resources that it is using
        g2.dispose();
    }
}