/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author ACER
 */
public class DisplayBoard extends JPanel {

    private Board board;
    private final int leftMargin = 0, rightMargin = 0, supMargin = 0, infMargin = 0, vGap = 0, hGap = 0;
    private int width, height;

    public DisplayBoard() {
        this(8, 12);
        this.board = new Board(8, 12);
    }

    public DisplayBoard(int w, int h) {
        height = (20 + vGap) * (h) + supMargin + infMargin;
        width = (20 + hGap) * (w) + leftMargin + rightMargin + 16 * 16;
        setVisible(true);
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D sprite = (Graphics2D) g;
        File imageFile;
        BufferedImage imageMat[][] = getSprites(board);
        int i, j;
        i = supMargin;
        for (BufferedImage[] row : imageMat) {
            j = leftMargin;
            drawSprite(sprite, Images.wall2, i, j);
            j += 16 + hGap;
            for (BufferedImage image : row) {
                drawSprite(sprite, image, i, j);
                j += 16 + hGap;
            }
            for (int k = j; k < j + (16 + vGap) * 16; k += 16 + vGap) {
                drawSprite(sprite, Images.wall2, i, k);
            }
            i += 16 + vGap;
        }
        for (j = 0; j < width - (16 + vGap); j += 16 + vGap) {
            drawSprite(sprite, Images.wall2, i, j);
        }
    }

    public void drawSprite(Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(image, y, x, this);
    }

    public static void main(String[] args) throws InterruptedException {
        DisplayBoard t = new DisplayBoard();
        new Images();
        t.board.randomFill();
        JFrame f = new JFrame("Puyo-Puyo");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(t.getWidth(), t.getHeight() + 7);
        f.show();
        f.getContentPane().add(t);

    }

    public static BufferedImage[][] getSprites(Board board) {
        BufferedImage image, sprites[][] = new BufferedImage[board.getHeight()][board.getWidth()];

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getBoard()[i][j] != null) {
                    switch (board.getBoard()[i][j].getColor()) {
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
                        default:
                            image = Images.clear;
                    }
                } else {
                    image = Images.gap;
                }
                sprites[i][j] = image;
            }
        }
        return sprites;
    }
}
