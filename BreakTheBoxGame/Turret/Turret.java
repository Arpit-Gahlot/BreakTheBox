/**
 * This class handles all the workings of the Turret
 */

package Turret;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Turret {

    //instance variables
    public int turretHorizontalPosition;
    public int turretDepth;
    public int shiftSpeed;
    public boolean isShooting;
    public boolean isNextShotReady;
    public int shootAnimationTimer;
    public int nextShotTimer;
    public BufferedImage turretLeft, turretMid, turretRight, turretTop, turretShootMid, turretShootTop;
    public String direction;
    public CannonBallQueue currentCannonBalls;
    public boolean canMoveLeft;
    public boolean canMoveRight;

    public GamePanel gp;
    KeyHandler keyH;

    //constructor
    public Turret(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        getTurretPortionImages();
        setDefaultValues();
    }

    //sets the values of the specified instance variables to default
    public void setDefaultValues() {

        turretHorizontalPosition = (gp.maxScreenColumn/2 - 1) * gp.tileSize;
        turretDepth = (gp.maxScreenRow - 1) * gp.tileSize;
        shiftSpeed = 4;
        isShooting = false;
        isNextShotReady = true;
        shootAnimationTimer = 0;
        nextShotTimer = 0;
        direction = "still";
        currentCannonBalls = new CannonBallQueue();
        canMoveLeft = true;
        canMoveRight = true;

    }

    //Assign the images of the different turret parts to the respective variables
    public void getTurretPortionImages() {

        try {
            turretLeft = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretLeftPortion.png"));
            turretRight = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretRightPortion.png"));
            turretMid = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretMidPortion.png"));
            turretShootMid = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretShootMidPortion.png"));
            turretTop = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretTopPortion.png"));
            turretShootTop = ImageIO.read(getClass().getResourceAsStream("/TurretDesign/turretShootTopPortion.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    //This method is called 60 times each second and handles all the movements of the Turret
    public void update() {

        if (!gp.cChecker.collisionHappened) {
            if (keyH.moveLeft) {
                if (canMoveLeft) {
                    direction = "movingLeft";
                    turretHorizontalPosition = turretHorizontalPosition - shiftSpeed;
                }
            } else if (keyH.moveRight) {
                if (canMoveRight) {
                    direction = "movingRight";
                    turretHorizontalPosition = turretHorizontalPosition + shiftSpeed;
                }
            } else {
                direction = "still";
            }

            //Turret will be ready again after 0.5 seconds
            if (keyH.shoot && isNextShotReady) {
                if (nextShotTimer >= 30) {
                    isShooting = true;
                    shoot();
                    nextShotTimer = 0;
                    shootAnimationTimer = 0;
                    isNextShotReady = false;
                }
            }

            if (nextShotTimer >= 30) {
                isNextShotReady = true;
            }

            //Turret position returns back to normal
            if (isShooting && shootAnimationTimer >= 10) {
                isShooting = false;
                shootAnimationTimer = 0;
            }

            CannonBallQueue.Node currentCannonBall = currentCannonBalls.head;

            //if the cannonBalls go off-screen, remove them
            while (currentCannonBall != null) {

                if (currentCannonBalls.head != null) {
                    if (currentCannonBalls.head.data.yCoordinate < -48) {
                        currentCannonBalls.removeCannonBall();
                    }
                }
                currentCannonBall.data.update();
                currentCannonBall = currentCannonBall.next;
            }

            gp.cChecker.checkTurretCollision();

            shootAnimationTimer = shootAnimationTimer + 1;
            nextShotTimer = nextShotTimer + 1;
        }
    }

    //Draws the images (the game is a sequence of images) on the screen
    public void draw(Graphics g) {
        BufferedImage shootPositionMid = null;
        BufferedImage shootPositionTop = null;

        if (isShooting) {
            shootPositionMid = turretShootMid;
            shootPositionTop = turretShootTop;
        }
        else {
            shootPositionMid = turretMid;
            shootPositionTop = turretTop;
        }

        g.drawImage(turretLeft, turretHorizontalPosition - gp.tileSize, turretDepth, gp.tileSize, gp.tileSize, null);
        g.drawImage(turretRight, turretHorizontalPosition + gp.tileSize, turretDepth, gp.tileSize, gp.tileSize, null);
        g.drawImage(shootPositionMid, turretHorizontalPosition, turretDepth, gp.tileSize, gp.tileSize, null);
        g.drawImage(shootPositionTop, turretHorizontalPosition, turretDepth - gp.tileSize, gp.tileSize, gp.tileSize, null);

        CannonBallQueue.Node currentCannonBall = currentCannonBalls.head;
        while (currentCannonBall != null) {
            currentCannonBall.data.draw(g, gp);
            currentCannonBall = currentCannonBall.next;
        }
    }

    //Method to add a new cannonBall to the queue
    public void shoot() {
        currentCannonBalls.addCannonBall(new CannonBall(turretHorizontalPosition, turretDepth - gp.tileSize));
    }

    public class CannonBallQueue {

        //A Node subclass where every Node corresponds to a single cannonBall
        public class Node {
            public CannonBall data;
            public Node next = null;

            public Node(){}
        }

        //instance variables
        public Node head;
        int count;
        Node tail;

        //constructor
        public CannonBallQueue() {
            head = null;
            count = 0;
            tail = null;
        }

        //adds the cannonBall to the back of the queue
        public void addCannonBall(CannonBall CannonBallToAdd) {

            Node n = new Node();
            n.data = CannonBallToAdd;

            if (head == null) {

                head = n;
                tail = n;
            }
            else if (head == tail) {

                head.next = n;
                tail = n;
            }
            else {

                tail.next = n;
                tail = tail.next;
            }
            count = count + 1;
        }

        //removes the cannonBall from the front of the queue
        public void removeCannonBall() {

            head = head.next;
            count = count - 1;
        }
    }
}
