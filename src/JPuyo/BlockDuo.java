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
public class BlockDuo {

    private Block pivot, extension;
    private Board board;
    private int degrees;

    public BlockDuo(int x, int y) {
        this.pivot = new Block(GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)], x, y);
        this.extension = new Block(GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)], x, y + 1);
        this.board = pivot.getBoard();
        degrees = 180;
    }

    public BlockDuo(Block pivot, Block exension) {
        this.pivot = pivot;
        this.extension = exension;
        this.extension.setPositionX(this.pivot.getPositionX());
        this.extension.setPositionY(this.pivot.getPositionY() + 1);
        degrees = 180;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public Block getPivot() {
        return pivot;
    }

    public Block getExtension() {
        return extension;
    }

    public void right() {
        if (degrees == 0) {
            if(extension.right())
                pivot.right();
        } else {
            if(pivot.right())
                extension.right();
        }
    }

    public void left() {
        if (degrees == 180) {
            if(extension.left())
                pivot.left();
        } else {
            if(pivot.left())
                extension.left();
        }
    }

    public void rotateR() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        degrees = (degrees + 90) % 360;
        int pos2[] = pivot.getPosition();
        pos2[0] += pos[degrees / 90][0];
        pos2[1] += pos[degrees / 90][1];
        extension.setPosition(pos2);
    }

    public void rotateL() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        degrees = (degrees + 270) % 360;
        int pos2[] = pivot.getPosition();
        pos2[0] += pos[degrees / 90][0];
        pos2[1] += pos[degrees / 90][1];
        extension.setPosition(pos2);
    }

    public void fall() {
        if (degrees == 270) {
            if(extension.fall())
                pivot.fall();
        } else {
            if(pivot.fall())
                extension.fall();
        }
    }

    public void drop() {
        if (degrees == 270) {
            extension.drop();
            pivot.drop();
        } else {
            pivot.drop();
            extension.drop();
        }
    }
    
    public void upadate(){
        board.placeInBoard(pivot);
        board.placeInBoard(extension);
        if(!pivot.isActive() || !extension.isActive()){
            drop();
        }
    }

}
