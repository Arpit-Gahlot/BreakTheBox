/**
 * This class handles the collisions for the BreakTheBox game
 */

package Collision;

import Main.GamePanel;
import Turret.Turret.CannonBallQueue;
import Box.BoxManager.BoxQueue;

public class CollisionChecker {

    //instance variables
    public GamePanel gp;
    public boolean collisionHappened;

    //constructor
    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
        collisionHappened = false;
    }

    //Method to check that the turret doesn't go off-screen
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

    //Method that checks for all the collisions in the game
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

                    //If the cannonBall and box touch, it's a hit
                    if (boxBottomYCoordinate >= cannonBallTopY) {
                        currentBox.data.boxHealthbar --;

                        if (currentCannonBall == gp.turret.currentCannonBalls.head) {
                            gp.turret.currentCannonBalls.removeCannonBall();
                            currentCannonBall = gp.turret.currentCannonBalls.head;
                        }
                        else {
                            previousCannonBall.next = currentCannonBall.next;
                            currentCannonBall = previousCannonBall.next;
                        }
                    }
                }

                if (currentCannonBall != null) {

                    previousCannonBall = currentCannonBall;
                    currentCannonBall = currentCannonBall.next;
                }
            }

            if ((boxLeftXCoordinate <= gp.turret.turretHorizontalPosition + 2 * gp.tileSize)
                    && (boxRightXCoordinate >= gp.turret.turretHorizontalPosition - gp.tileSize)) {

                if ((boxLeftXCoordinate <= gp.turret.turretHorizontalPosition + gp.tileSize)
                        && (boxRightXCoordinate >= gp.turret.turretHorizontalPosition)) {

                    if (boxBottomYCoordinate >= gp.turret.turretDepth - gp.tileSize) {
                        collisionHappened = true;
                        break;
                    }
                }
                if (boxBottomYCoordinate >= gp.turret.turretDepth + 15) {
                    collisionHappened = true;
                    break;
                }
            }

            //check if a box reaches the bottom, this will finish the game
            if (boxBottomYCoordinate >= gp.screenHeight) {
                collisionHappened = true;
                break;
            }

            //if a box health reaches 0, it is destroyed
            if (currentBox.data.boxHealthbar <= 0) {
                if (currentBox == gp.boxManager.currentBoxes.head) {
                    gp.boxManager.currentBoxes.removeBox();
                }
                else {
                    previousBox.next = currentBox.next;
                }
                gp.playerScore ++;
            }

            previousBox = currentBox;
            currentBox = currentBox.next;
        }
    }
}
