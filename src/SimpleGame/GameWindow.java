/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGame;

import JPuyo.BoardPanel;
import JPuyo.Images;

/**
 *
 * @author eugeniolr
 */
public class GameWindow extends javax.swing.JFrame {
    
    /**
     *
     */
    public BoardPanel boardPanel;
            
    /**
     * Creates new form GameWindow
     */
    public GameWindow() {
        initComponents();
        prepareGame();
    }
    
    private void prepareGame(){
        new Images();
        this.boardPanel = new BoardPanel(8, 12);
        GameLoop gl = new GameLoop(this);
        this.add(boardPanel);
        gl.start();
    }
    
    /**
     *
     * @return
     */
    public javax.swing.JLabel getPointsLabel(){
        return this.pointsLabel;
    }
    
    /**
     *
     * @return
     */
    public BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pointsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pointsLabel.setText("35 points");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(173, 427, Short.MAX_VALUE)
                .addComponent(pointsLabel)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(pointsLabel)
                .addGap(91, 91, 91))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel pointsLabel;
    // End of variables declaration//GEN-END:variables
}
