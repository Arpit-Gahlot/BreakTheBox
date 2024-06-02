package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {

    public boolean moveLeft;
    public boolean moveRight;
    public boolean shoot;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            moveLeft = true;
            moveRight = false;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            moveLeft = false;
            moveRight = true;
        }
        else if (keyCode == KeyEvent.VK_SPACE) {
            shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            moveLeft = false;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight = false;
        }
        else if (keyCode == KeyEvent.VK_SPACE) {
            shoot = false;
        }
    }
}
