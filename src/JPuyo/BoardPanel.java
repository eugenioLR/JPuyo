/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

/**
 *
 * @author ACER
 */
public class BoardPanel extends JPanel {

    private Board board;
    private final int leftMargin  = 0, 
                      rightMargin = 0, 
                      supMargin   = 0, 
                      infMargin   = 0, 
                      vGap        = 0, 
                      hGap        = 0;
    private int width, height;

    /**
     * Constructor for the class BoardPanel
     */
    public BoardPanel() {
        this(8, 12);
    }

    /**
     * Constructor for the class BoardPanel given the width and height
     * @param w
     * @param h
     */
    public BoardPanel(int w, int h) {
        height = (20 + vGap) * (h) + supMargin + infMargin;
        width = (20 + hGap) * (w) + leftMargin + rightMargin + 16 * 4;
        setVisible(true);
        this.board = new Board(w, h);
        setBackground(Color.decode("#0F0F0F"));
    }

    /**
     *
     * @return
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Paints the board into the screen.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D sprite = (Graphics2D) g;
        File imageFile;
        BufferedImage imageMat[][] = getSprites(board);
        int i, j, r, c;
        r = 0;
        i = supMargin;
        for (BufferedImage[] row : imageMat) {
            j = leftMargin;
            drawSprite(sprite, Images.wall2, i, j);
            j += 16 + hGap;
            
            for (BufferedImage image : row) {
                drawSprite(sprite, image, i, j);
                j += 16 + hGap;
            }
            
            c = 0;
            for (int k = j; k <= j + (16 + vGap) * 16; k += 16 + vGap) {
                if(1 <= c && c <= 3 &&  3 <= r && r <= 6){
                    if(board.getSequence().size() > 0 && c == 2){
                        if(r == 4){
                            drawSprite(sprite, blockToImage(board.nextBlock().getExtension()), i, k);
                        }else if(r==5){
                            drawSprite(sprite, blockToImage(board.nextBlock().getPivot()), i, k);
                        }
                    }else{
                        drawSprite(sprite, Images.gap, i, k);
                    }
                }else{
                    drawSprite(sprite, Images.wall2, i, k);
                }
                c++;
            }
            i += 16 + vGap;
            r++;
        }
        for (j = 0; j <= width - (16 + vGap); j += 16 + vGap) {
            drawSprite(sprite, Images.wall2, i, j);
        }
    }

    /**
     * Paints a sprite into the screen.
     * @param g2d
     * @param image
     * @param x
     * @param y
     */
    private void drawSprite(Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(image, y, x, this);
    }

    /**
     * returns the sprites representing the blocks on the board
     * @param board
     * @return
     */
    private BufferedImage[][] getSprites(Board board) {
        BufferedImage image, sprites[][] = new BufferedImage[board.getHeight()][board.getWidth()];
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getBoard()[i][j] != null) {
                    image = blockToImage(board.getBoard()[i][j]);
                } else {
                    image = Images.gap;
                }
                sprites[i][j] = image;
            }
        }
        return sprites;
    }
    
    private BufferedImage blockToImage(Block block){
        BufferedImage image;
        switch (block.getColor()) {
            case 'R':
                image = Images.red;
                break;
            case 'B':
                image = Images.blue;
                break;
            case 'G':
                image = Images.green;
                break;
            case 'P':
                image = Images.purple;
                break;
            case 'O':
                image = Images.orange;
                break;
            case 'Y':
                image = Images.yellow;
                break;
            case 'X':
            default:
                image = Images.clear;
        }
        return image;
    }
}
