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
    private final int right,left,rotateR,rotateL,drop,fall;

    /**
     * Constructor for the class KeyManager
     * @param layout
     */
    public KeyManager(int layout) {
        super();
        turnActive = false;
        switch(layout){
            case 0:
                right = KeyEvent.VK_D;
                left = KeyEvent.VK_A;
                rotateR = KeyEvent.VK_E;
                rotateL = KeyEvent.VK_Q;
                drop = KeyEvent.VK_W;
                fall = KeyEvent.VK_S;
                break;
            case 1:
                right = KeyEvent.VK_RIGHT;
                left = KeyEvent.VK_LEFT;
                rotateR = KeyEvent.VK_M;
                rotateL = KeyEvent.VK_N;
                drop = KeyEvent.VK_UP;
                fall = KeyEvent.VK_DOWN;
                break;
            default:
                right = KeyEvent.VK_D;
                left = KeyEvent.VK_A;
                rotateR = KeyEvent.VK_E;
                rotateL = KeyEvent.VK_Q;
                drop = KeyEvent.VK_W;
                fall = KeyEvent.VK_S;
        }
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
            int code = e.getKeyCode();
            
            if(code == this.right){
                currentBlock.right();
            }else if(code == this.left){
                currentBlock.left();
            }else if(code == this.fall){
                currentBlock.fall();
            }else if(code == this.drop){
                currentBlock.drop();
            }else if(code == this.rotateL){
                currentBlock.rotateL();
            }else if(code == this.rotateR){
                currentBlock.rotateR();
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
