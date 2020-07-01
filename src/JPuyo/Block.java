/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import DuoGame.GameLoop;

/**
 *
 * @author Eugenio
 */
public class Block {

    private char color;
    private Board board;
    private int position[] = new int[2];
    private boolean active;
    
    /**
     * Constructor for the class Block
     */
    public Block() {
        this.color = GameLoop.getCOLORS().get(GameLoop.randInt(0, GameLoop.getCOLORS().size() - 1));
        this.position[0] = 0;
        this.position[1] = 0;
        this.active = true;
    }
    
    /**
     * Constructor for the class Block
     * @param color
     */
    public Block(char color) {
        this.color = color;
        this.position[0] = 0;
        this.position[1] = 0;
        this.active = true;
    }

    /**
     * Constructor for the class Block
     * @param color
     * @param positionX
     * @param positionY
     */
    public Block(char color, int positionX, int positionY) {
        this.color = color;
        this.position[0] = positionX;
        this.position[1] = positionY;
        this.active = true;
    }

    /**
     * Constructor for the class Block
     * @param color
     * @param positionY
     * @param positionX
     * @param active
     */
    public Block(char color, int positionX, int positionY, boolean active) {
        this.color = color;
        this.position[0] = positionX;
        this.position[1] = positionY;
        this.active = active;
    }

    /**
     *
     * @return
     */
    public char getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(char color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     *
     * @return
     */
    public int[] getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * @return
     */
    public boolean setPosition(int[] position) {
        boolean xInBounds = 0 <= position[0] && position[0] < board.getWidth();
        boolean yInBounds = 0 <= position[1] && position[1] < board.getHeight();
        if (xInBounds && yInBounds) {
            this.position = position;
        }
        return xInBounds && yInBounds;
    }

    /**
     *
     * @return
     */
    public int getPositionX() {
        return position[0];
    }

    /**
     *
     * @return
     */
    public int getPositionY() {
        return position[1];
    }

    /**
     *
     * @param x
     * @return
     */
    public boolean setPositionX(int x) {
        boolean xInBounds = x > 0 && x < board.getWidth();
        if (xInBounds) {
            this.position[0] = x;
        }
        return xInBounds;
    }

    /**
     *
     * @param y
     * @return
     */
    public boolean setPositionY(int y) {
        boolean yInBounds = y > 0 && y < board.getHeight();
        if (yInBounds) {
            this.position[1] = y;
        }
        return yInBounds;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     */
    public void activate() {
        this.active = true;
    }

    /**
     *
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * makes the block fall one position, if it can't, it will deactivate
     * @return
     */
    public boolean fall() {
        int below[] = {position[0], position[1] + 1};
        boolean canFall = board.getBlockAt(below) == null && below[1] < board.getHeight();
        if (canFall) {
            board.clearBlock(position[0], position[1]);
            this.position[1]++;
        }
        active = canFall;
        update();
        return canFall;
    }
    
    /**
     * checks if the block can fall or not
     * @return
     */
    public boolean canFall() {
        int below[] = {position[0], position[1] + 1};
        return board.getBlockAt(below) == null && below[1] < board.getHeight();
    }

    /**
     * drops the block to the lowest position possible
     */
    public void drop() {
        int below[] = {position[0], position[1] + 1};
        while (board.getBlockAt(below) == null && below[1] < board.getHeight()) {
            board.clearBlock(position[0], position[1]);
            this.position[1]++;
            below[1]++;
        }
        update();
        active = false;
    }

    /**
     * moves the block to the right
     * @return
     */
    public boolean right() {
        int right[] = {position[0] + 1, position[1]};
        boolean canMove = board.getBlockAt(right) == null && right[0] < board.getWidth();
        if (canMove) {
            board.clearBlock(position[0], position[1]);
            this.position[0]++;
        }
        update();
        return canMove;
    }
    
    /**
     * Checks if the block can move to the right
     * @return
     */
    public boolean canRight(){
        int right[] = {position[0] + 1, position[1]};
        return board.getBlockAt(right) == null && right[0] < board.getWidth();
    }

    /**
     * moves the block to the left
     * @return
     */
    public boolean left() {
        int left[] = {position[0] - 1, position[1]};
        boolean canMove = board.getBlockAt(left) == null && left[0] >= 0;
        if (canMove) {
            board.clearBlock(position[0], position[1]);
            this.position[0]--;
        }
        update();
        return canMove;
    }
    
    /**
     * checks if the block can move to the left
     * @return
     */
    public boolean canLeft(){
        int right[] = {position[0] - 1, position[1]};
        return board.getBlockAt(right) == null && right[0] < board.getWidth();
    }

    /**
     * places the block on the board
     */
    public void update() {
        board.placeInBoard(this);
    }
}
