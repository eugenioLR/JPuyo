 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.Block;
import JPuyo.Board;
/**
 *
 * @author ACER
 */

public class BlockDuo {

    private Block pivot, extension;
    private Board board;
    private int degrees;

    /**
     *
     * @param x
     * @param y
     */
    public BlockDuo(int x, int y) {
        this.pivot = new Block(GameLoopDuo.getCOLORS().get(GameLoopDuo.randInt(0, GameLoopDuo.getCOLORS().size() - 1)), x, y + 1);
        this.extension = new Block(GameLoopDuo.getCOLORS().get(GameLoopDuo.randInt(0, GameLoopDuo.getCOLORS().size() - 1)), x, y);
        degrees = 90;
    }
    
    /**
     *
     * @param pivot
     * @param exension
     */
    public BlockDuo(Block pivot, Block exension) {
        this.pivot = pivot;
        this.extension = exension;
        this.extension.setPositionX(this.pivot.getPositionX());
        this.extension.setPositionY(this.pivot.getPositionY() - 1);
        degrees = 90;
    }

    /**
     *
     * @return
     */
    public int getDegrees() {
        return degrees;
    }

    /**
     *
     * @param degrees
     */
    public void setDegrees(int degrees) {
        this.degrees = degrees % 360;
    }

    /**
     *
     * @return
     */
    public Block getPivot() {
        return this.pivot;
    }

    /**
     *
     * @return
     */
    public Block getExtension() {
        return this.extension;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    /**
     *
     */
    public void right() {
        if (this.degrees == 0) {
            if (this.extension.right()) {
                this.pivot.right();
            }
        } else {
            if (this.pivot.right()) {
                this.extension.right();
            }
        }
    }

    /**
     *
     */
    public void left() {
        if (this.degrees == 180) {
            if (this.extension.left()) {
                this.pivot.left();
            }
        } else {
            if (this.pivot.left()) {
                this.extension.left();
            }
        }
    }

    /**
     *
     */
    public void rotateR() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        this.degrees = Math.floorMod(this.degrees - 90, 360);
        
        this.board.clearBlock(this.extension.getPosition());
        this.extension.setPositionX(this.pivot.getPositionX() + pos[this.degrees / 90][0]);
        this.extension.setPositionY(this.pivot.getPositionY() + pos[this.degrees / 90][1]);
        this.board.placeInBoard(this.extension);
        
    }

    /**
     *
     */
    public void rotateL() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        this.degrees = Math.floorMod(this.degrees + 90, 360);
        
        this.board.clearBlock(this.extension.getPosition());
        this.extension.setPositionX(this.pivot.getPositionX() + pos[this.degrees / 90][0]);
        this.extension.setPositionY(this.pivot.getPositionY() + pos[this.degrees / 90][1]);
        this.board.placeInBoard(this.extension);
    }

    /**
     *
     */
    public void fall() {
        if (this.degrees == 90) {
            if (this.pivot.fall()) {
                this.extension.fall();
            }
        } else {
            if (this.extension.fall()) {
                this.pivot.fall();
            }
        }
    }

    /**
     *
     */
    public void drop() {
        if (this.degrees == 270) {
            this.extension.drop();
            this.pivot.drop();
        } else {
            this.pivot.drop();
            this.extension.drop();
        }
    }

    /**
     *
     */
    public void update() {
        this.board.placeInBoard(this.pivot);
        this.board.placeInBoard(this.extension);
        if (!this.pivot.isActive() || !this.extension.isActive()) {
            drop();
        }
    }

}
