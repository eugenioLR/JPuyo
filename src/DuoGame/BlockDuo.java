 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.Block;
import JPuyo.Board;
import SimpleGame.GameLoop;

/**
 *
 * @author ACER
 */
public class BlockDuo {

    private Block pivot, extension;
    private Board board;
    private int degrees;

    public BlockDuo(int x, int y) {
        this.pivot = new Block(GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)], x, y);
        this.extension = new Block(GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)], x, y - 1);
        this.board = pivot.getBoard();
        degrees = 90;
    }

    public BlockDuo(Block pivot, Block exension) {
        this.pivot = pivot;
        this.extension = exension;
        this.extension.setPositionX(this.pivot.getPositionX());
        this.extension.setPositionY(this.pivot.getPositionY() - 1);
        degrees = 90;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees % 360;
    }

    public Block getPivot() {
        return this.pivot;
    }

    public Block getExtension() {
        return this.extension;
    }

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

    public void rotateR() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        this.degrees = (this.degrees - 90) % 360;
        int pos2[] = this.pivot.getPosition();
        pos2[0] += pos[this.degrees / 90][0];
        pos2[1] += pos[this.degrees / 90][1];
        this.extension.setPosition(pos2);
    }

    public void rotateL() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        this.degrees = (this.degrees + 90) % 360;
        int pos2[] = this.pivot.getPosition();
        pos2[0] += pos[this.degrees / 90][0];
        pos2[1] += pos[this.degrees / 90][1];
        this.extension.setPosition(pos2);
    }

    public void fall() {
        if (this.degrees == 270) {
            if (this.extension.fall()) {
                this.pivot.fall();
            }
        } else {
            if (this.pivot.fall()) {
                this.extension.fall();
            }
        }
    }

    public void drop() {
        if (this.degrees == 270) {
            this.extension.drop();
            this.pivot.drop();
        } else {
            this.pivot.drop();
            this.extension.drop();
        }
    }

    public void upadate() {
        this.board.placeInBoard(this.pivot);
        this.board.placeInBoard(this.extension);
        if (!this.pivot.isActive() || !this.extension.isActive()) {
            drop();
        }
    }

}
