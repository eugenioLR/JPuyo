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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author eugeniolr
 */
public class KeyManager implements KeyListener {

    private BlockDuo currentBlock;
    private boolean turnActive;

    /**
     * Constructor for the class KeyManager
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
    
    /**
     * allows the player to control the blocks
     */
    public synchronized void activateTurn(){
        turnActive = true;
        notifyAll();
    }
    
    /**
     * makes the player wait until the turn is active
     */
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
