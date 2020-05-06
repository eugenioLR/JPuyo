/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

/**
 *
 * @author ACER
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManagerDuo implements KeyListener {

    private BlockDuo currentBlock;

    public KeyManagerDuo() {
        super();
    }

    public BlockDuo getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(BlockDuo currentBlock) {
        this.currentBlock = currentBlock;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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

    
    
    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
