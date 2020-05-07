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

/**
 *
 * @author eugeniolr
 */
public class KeyManagerDuo implements KeyListener {

    private BlockDuo currentBlock;

    /**
     *
     */
    public KeyManagerDuo() {
        super();
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
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
