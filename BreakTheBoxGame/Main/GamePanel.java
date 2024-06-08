/**
 * This class contains all the details of the Game screen and other basic game settings
 * IMPORTANT: THIS CLASS WAS CREATED WITH THE HELP FROM @RYISNOW ON YOUTUBE.
 */

package Main;

import Collision.CollisionChecker;
import Turret.Turret;
import Box.BoxManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends Canvas implements Runnable {

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
    public BufferedImage backgroundImage;
    public int playerScore;

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
    public CollisionChecker cChecker = new CollisionChecker(this);

    //Constructor
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //set the size of the screen
        this.addKeyListener(keyH); //Add the key handler to the Game panel
        this.setFocusable(true); //Game Panel can be focused to receive key input
        this.setBackground(Color.BLACK);
        getBackgroundImage(); //assigns the background image to the backgroundImage variable
        playerScore = 0;
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
        createBufferStrategy(2); //All the drawing will be done in an off-screen paint buffer (higher performance)

        while (gameThread != null) {

            //check current time
            currentTime = System.nanoTime();
            delta = delta + ((currentTime - lastTime) / drawInterval);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                gameTimer++;
            }
        }
    }

    //update information such as player positions
    public void update() {

        if (keyH.gameStart) {

            turret.update();
            boxManager.update();
            cChecker.checkForCollision();
        }
    }

    //draw the objects
    public void draw() {

        Graphics myGraphics = getBufferStrategy().getDrawGraphics();

        if (!keyH.gameStart) {

            startGameScreen(myGraphics);
        }
        else {

            //Graphics2D extends the Graphics class and provides more coordinate control, etc compared to Graphics.
            Graphics2D myGraphics2D = (Graphics2D) myGraphics;

            myGraphics2D.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
            turret.draw(myGraphics2D);
            boxManager.draw(myGraphics2D);

            if (cChecker.collisionHappened) {
                gameOver(myGraphics);
            }
            else {
                showScore(myGraphics2D);
            }

            //dispose of this graphics context and release any system resources that it is using
            myGraphics2D.dispose();
        }

        getBufferStrategy().show();

    }

    //Method that handles what will be shown initially as the game starts
    public void startGameScreen(Graphics g) {

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Break The Box", (screenWidth - metrics.stringWidth("Break The Box"))/2, screenHeight/2 - tileSize);

        g.setColor(Color.cyan);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Spacebar to shoot, left and right arrow keys to move", (screenWidth - metrics.stringWidth("Spacebar to shoot, left and right arrow keys to move"))/2, screenHeight/2 + tileSize);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Press any key to start", (screenWidth - metrics.stringWidth("Press any key to start"))/2, screenHeight/2 + 3 * tileSize);
    }

    //This method is used for displaying the score during the game
    public void showScore(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Score: " + playerScore , screenWidth - 3 * tileSize + 20, tileSize);
    }

    //This method pops up the "Game Over" text as soon as a box reaches the bottom or touches the turret
    public void gameOver(Graphics g) {

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game Over", screenWidth/2 - 4 * tileSize, screenHeight/2);

        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        g.drawString("Your score is: " + playerScore, screenWidth/2 - 4 * tileSize + 20, screenHeight/2 + 2 * tileSize);
    }

    //Assigns the background image to the "backgroundImage" variable
    public void getBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Main/BreakTheBoxbg.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}