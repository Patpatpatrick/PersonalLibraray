package ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class KeyboardActionListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    abstract public void keyPressed(KeyEvent e);

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
