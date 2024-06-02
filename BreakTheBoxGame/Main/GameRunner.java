/**
 * @author - Arpit Gahlot
 * This class will run the Break The Box Game.
 */

package Main;

import javax.swing.JFrame;

public class GameRunner {
    public static void main(String[] args) {

        //The display window for the Game
        JFrame window = new JFrame();

        //Clicking the close button closes the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Window cannot be resized by the user
        window.setResizable(false);

        //Sets the title of the window
        window.setTitle("Break The Box");

        //Create a GamePanel object and add it to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        //causes the window to be resized as per the size and layout of its subcomponents
        window.pack();

        //No location set for the window (will be displayed in the center if parameter is null)
        window.setLocationRelativeTo(null);

        //Allows the user to see the window
        window.setVisible(true);

        //start the game
        gamePanel.startGameThread();
    }
}
