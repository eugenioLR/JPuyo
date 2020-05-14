/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

/**
 *
 * @author ACER
 */
import DuoGame.BlockDuo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eugeniolr
 */
public class KeyManager implements KeyListener {

    private BlockDuo currentBlock;
    private boolean turnActive;

    /**
     *
     */
    public KeyManager() {
        super();
        turnActive = false;
    }

    /**
     *
     * @return
     */
    public BlockDuo getCurrentBlock() {
        return currentBlock;
    }

    /**
     *
     * @param currentBlock
     */
    public void setCurrentBlock(BlockDuo currentBlock) {
        this.currentBlock = currentBlock;
    }
    
    public synchronized void activateTurn(){
        turnActive = true;
        notifyAll();
    }
    
    public void deactivateTurn(){
        turnActive = false;
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public synchronized void keyPressed(KeyEvent e) {
        while(!turnActive){
            try {
                wait();
            } catch (InterruptedException ex) {}
        }
        if (currentBlock != null) {
            switch (e.getKeyChar()) {
                case 'a':
                    currentBlock.left();
                    break;
                case 'd':
                    currentBlock.right();
                    break;
                case 's':
                    currentBlock.fall();
                    break;
                case 'w':
                    currentBlock.drop();
                    break;
                case 'q':
                    currentBlock.rotateL();
                    break;
                case 'e':
                    currentBlock.rotateR();
                    break;
            }
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
