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

    private final Block pivot, extension;
    private Board board;
    private int degrees;

    /**
     *
     * @param color1
     * @param color2
     */
    public BlockDuo(char color1, char color2){
        this.pivot = new Block(color1);
        this.extension = new Block(color2);
        degrees = 90;
    }
    
    /**
     * Constructor for the class BlockDuo
     * @param x
     * @param y
     */
    public BlockDuo(int x, int y) {
        this.pivot = new Block(Colors.getCOLORS().get(GameLoop.randInt(0, Colors.getCOLORS().size() - 1)), x, y + 1);
        this.extension = new Block(Colors.getCOLORS().get(GameLoop.randInt(0, Colors.getCOLORS().size() - 1)), x, y);
        degrees = 90;
    }
    
    /**
     * Constructor for the class BlockDuo
     * @param x
     * @param y
     * @param color1
     * @param color2
     */
    public BlockDuo(int x, int y, char color1, char color2) {
        this.pivot = new Block(color1, x, y + 1);
        this.extension = new Block(color2, x, y);
        degrees = 90;
    }
    
    /**
     * Constructor for the class BlockDuo given two blocks
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

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
        this.pivot.setBoard(board);
        this.extension.setBoard(board);
    }
    
    /**
     * Moves the duo to right only if both of them can.
     */
    public void right() {
        switch (this.degrees) {
            case 0:
                if (this.extension.right()) {
                    this.pivot.right();
                }   break;
            case 90:
            case 270:
                if (this.pivot.canRight() && this.extension.canRight()){
                    this.pivot.right();
                    this.extension.right();
                }   break;
            default:
                if (this.pivot.right()) {
                    this.extension.right();
                }   break;
        }
    }

    /**
     * Moves the duo to left only if both of them can.
     */
    public void left() {
        switch (this.degrees) {
            case 180:
                if (this.extension.left()) {
                    this.pivot.left();
                }   break;
            case 90:
            case 270:
                if (this.pivot.canLeft() && this.extension.canLeft()){
                    this.pivot.left();
                    this.extension.left();
                }   break;
            default:
                if (this.pivot.left()) {
                    this.extension.left();
                }   break;
        }
    }

    /**
     * Rotates the extension arround the pivot counterclockwise
     */
    public void rotateR() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        int newDegrees = Math.floorMod(this.degrees - 90, 360);
        int newPos[] = {this.pivot.getPositionX() + pos[newDegrees / 90][0], this.pivot.getPositionY() + pos[newDegrees / 90][1]};
        
        
        boolean canRotate = this.board.getBlockAt(newPos) == null 
                && 0 <= newPos[0] && newPos[0] < this.board.getWidth()
                && 0 <= newPos[1] && newPos[1] < this.board.getHeight();
        
        if(canRotate){
            this.degrees = newDegrees;
            this.board.clearBlock(this.extension.getPosition());
            this.extension.setPosition(newPos);
            this.board.placeInBoard(this.extension);
        }
        
    }

    /**
     * Rotates the extension arround the pivot clockwise
     */
    public void rotateL() {
        int pos[][] = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
        int newDegrees = Math.floorMod(this.degrees + 90, 360);
        int newPos[] = {this.pivot.getPositionX() + pos[newDegrees / 90][0], this.pivot.getPositionY() + pos[newDegrees / 90][1]};
        
        
        boolean canRotate = this.board.getBlockAt(newPos) == null 
                && 0 <= newPos[0] && newPos[0] < this.board.getWidth()
                && 0 <= newPos[1] && newPos[1] < this.board.getHeight();
        
        if(canRotate){
            this.degrees = newDegrees;
            this.board.clearBlock(this.extension.getPosition());
            this.extension.setPosition(newPos);
            this.board.placeInBoard(this.extension);
        }
    }

    /**
     * Makes the duo fall one block, if it hits an obstacle, the duo will deactivate
     */
    public void fall() {
        if (this.degrees == 90) {
            if (this.pivot.fall()) {
                this.extension.fall();
            }else{
                this.extension.deactivate();
            }
        } else {
            if (this.extension.fall()) {
                this.pivot.fall();
            }else{
                this.pivot.deactivate();
            }
        }
    }

    /**
     * Makes the duo drop to the lowest position posible, then both will deactivate.
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
     * places both blocks in the board and makes them drop if both are deactivated 
     */
    public void update() {
        this.board.placeInBoard(this.pivot);
        this.board.placeInBoard(this.extension);
        if (!this.pivot.isActive() && !this.extension.isActive()) {
            drop();
        }
    }

    /**
     *
     * @param i
     */
    public void setPosition(int[] i) {
    }

}
