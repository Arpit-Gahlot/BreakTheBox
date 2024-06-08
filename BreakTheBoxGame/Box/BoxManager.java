/**
 * This class handles the logic of all the boxes in the game
 */

package Box;

import Main.GamePanel;

import java.awt.*;

public class BoxManager {

    //instance variables
    GamePanel gp;
    public BoxQueue currentBoxes;

    //constructor
    public BoxManager(GamePanel gp) {

        this.gp = gp;
        currentBoxes = new BoxQueue();
    }

    //Method to add a box to the current boxes queue
    public void createBox() {
        currentBoxes.addBox(new Box(gp));
    }

    //This method is called 60 times each second and handles the functioning of the boxes
    public void update() {

        if (!gp.cChecker.collisionHappened) {
            BoxQueue.Node currentNode = currentBoxes.head;
            while (currentNode != null) {

                currentNode.data.colorManager();
                currentNode.data.yCoordinate = currentNode.data.yCoordinate + 1;
                currentNode = currentNode.next;
            }

            if (gp.gameTimer % 90 == 0) {
                createBox();
            }
        }
    }

    //Method to draw the boxes on the GamePanel
    public void draw(Graphics g) {

        BoxQueue.Node currentNode = currentBoxes.head;
        while (currentNode != null) {

            g.setColor(currentNode.data.boxColor);
            g.fillRect(currentNode.data.xCoordinate, currentNode.data.yCoordinate, gp.tileSize, gp.tileSize);

            g.setColor(Color.darkGray);
            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("" + currentNode.data.boxHealthbar, currentNode.data.xCoordinate + 20, currentNode.data.yCoordinate + gp.tileSize - 15);

            currentNode = currentNode.next;
        }
    }
    public class BoxQueue {

        //A Node subclass where every Node corresponds to a single box
        public class Node {
            public Box data;
            public Node next = null;

            public Node(){}
        }

        //instance variables
        public Node head;
        int count;
        Node tail;

        //constructor
        public BoxQueue() {
            head = null;
            count = 0;
            tail = null;
        }

        //adds the box to the back of the queue
        public void addBox(Box box) {

            Node n = new Node();
            n.data = box;

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

        //removes the box from the front of the queue
        public void removeBox() {

            head = head.next;
            count = count - 1;
        }
    }

}
