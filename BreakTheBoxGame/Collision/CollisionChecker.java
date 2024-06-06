package Collision;

import Main.GamePanel;
import Turret.Turret.CannonBallQueue;
import Box.BoxManager.BoxQueue;

public class CollisionChecker {

    //instance variables
    public GamePanel gp;
    public boolean collisionHappened;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
        collisionHappened = false;
    }

    public void checkTurretCollision() {

        if (gp.turret.turretHorizontalPosition - gp.tileSize <= 0 ) {
            gp.turret.canMoveLeft = false;
        }
        else {
            gp.turret.canMoveLeft = true;
        }

        if (gp.turret.turretHorizontalPosition + 2 * gp.tileSize >= gp.maxScreenColumn * gp.tileSize) {
            gp.turret.canMoveRight = false;
        }
        else {
            gp.turret.canMoveRight = true;
        }
    }

    public void checkForCollision() {

        BoxQueue.Node currentBox = gp.boxManager.currentBoxes.head;
        BoxQueue.Node previousBox = null;

        while (currentBox != null) {

            int boxLeftXCoordinate = currentBox.data.xCoordinate;
            int boxRightXCoordinate = currentBox.data.xCoordinate + gp.tileSize;
            int boxBottomYCoordinate = currentBox.data.yCoordinate + gp.tileSize;

            CannonBallQueue.Node currentCannonBall = gp.turret.currentCannonBalls.head;
            CannonBallQueue.Node previousCannonBall = null;
            while (currentCannonBall != null) {

                int cannonBallLeftX = currentCannonBall.data.xCoordinate + 10;
                int cannonBallRightX = currentCannonBall.data.xCoordinate + 38;
                int cannonBallTopY = currentCannonBall.data.yCoordinate;

                //check that box is in the cannonball's range
                if (((cannonBallRightX >= boxLeftXCoordinate) && (cannonBallRightX <= boxRightXCoordinate))
                        || ((cannonBallLeftX <= boxRightXCoordinate) && (cannonBallLeftX >= boxLeftXCoordinate))){

                    if (boxBottomYCoordinate >= cannonBallTopY) {
                        currentBox.data.boxHealthbar --;

                        if (currentCannonBall == gp.turret.currentCannonBalls.head) {
                            gp.turret.currentCannonBalls.removeCannonBall();
                        }
                        else {
                            previousCannonBall.next = currentCannonBall.next;
                        }
                    }
                }

                previousCannonBall = currentCannonBall;
                currentCannonBall = currentCannonBall.next;
            }

            //check if a box reaches the bottom
            if (boxBottomYCoordinate >= gp.screenHeight) {
                collisionHappened = true;
                break;
            }

            if (currentBox.data.boxHealthbar <= 0) {
                if (currentBox == gp.boxManager.currentBoxes.head) {
                    gp.boxManager.currentBoxes.removeBox();
                }
                else {
                    previousBox.next = currentBox.next;
                }
            }

            previousBox = currentBox;
            currentBox = currentBox.next;
        }
    }
}
