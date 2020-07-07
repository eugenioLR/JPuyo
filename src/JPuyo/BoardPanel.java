/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.awt.*;
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
                      supMargin   = 0,
                      vGap        = 0, 
                      hGap        = 0,
                      floorDepth  = 1, //1 looks better
                      rightWall   = 5, //5 looks better
                      leftWall    = 1; //1 looks better
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
        super();
        height = (h + floorDepth)*(16 + vGap) + supMargin;
        width = (w + leftWall + rightWall)*(16 + hGap) + leftMargin;
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
        int i, j, r;
        r = 0;
        i = supMargin;
        for (BufferedImage[] row : imageMat) {
            j = leftMargin;
            
            for (int k = 0; k < this.leftWall; k++) {
                drawSprite(sprite, ImageLoader.wall2, i, j);
                j += 16 + hGap;
            }
            
            for (BufferedImage image : row) {
                drawSprite(sprite, image, i, j);
                j += 16 + hGap;
            }
            
            for (int k = 0; k < this.rightWall; k++) {
                if(1 <= k && k <= 3 &&  3 <= r && r <= 6){
                    if(board.getSequence().size() > 0 && k == 2){
                        if(r == 4){
                            drawSprite(sprite, blockToImage(board.nextBlock().getExtension()), i, j);
                        }else if(r==5){
                            drawSprite(sprite, blockToImage(board.nextBlock().getPivot()), i, j);
                        }
                    }else{
                        drawSprite(sprite, ImageLoader.gap, i, j);
                    }
                }else{
                    drawSprite(sprite, ImageLoader.wall2, i, j);
                }
                j += 16 + hGap;
            }
            i += 16 + vGap;
            r++;
        }
        
        for (int f = 0; f < this.floorDepth; f++){
            j = leftMargin;
            for (int k = 0; k < this.rightWall + this.leftWall + board.getWidth(); k++) {
                drawSprite(sprite, ImageLoader.wall2, i + (16 + vGap)*f, j);
                j += 16 + hGap;
            }
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
                    image = ImageLoader.gap;
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
                image = ImageLoader.red;
                break;
            case 'B':
                image = ImageLoader.blue;
                break;
            case 'G':
                image = ImageLoader.green;
                break;
            case 'P':
                image = ImageLoader.purple;
                break;
            case 'O':
                image = ImageLoader.orange;
                break;
            case 'Y':
                image = ImageLoader.yellow;
                break;
            case 'X':
            default:
                image = ImageLoader.clear;
        }
        return image;
    }
}
