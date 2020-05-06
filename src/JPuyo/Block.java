/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import SimpleGame.GameLoop;

/**
 *
 * @author Eugenio
 */
public class Block {

    private char color;
    private Board board;
    private int position[] = new int[2];
    private boolean active;
    
    public Block() {
        this.color = GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)];
        this.position[0] = 0;
        this.position[1] = 0;
        this.active = true;
    }
    
    public Block(char color) {
        this.color = color;
        this.position[0] = 0;
        this.position[1] = 0;
        this.active = true;
    }

    public Block(char color, int positionX, int positionY) {
        this.color = color;
        this.position[0] = positionX;
        this.position[1] = positionY;
        this.active = true;
    }

    public Block(char color, int positionX, int positionY, boolean active) {
        this.color = color;
        this.position[0] = positionX;
        this.position[1] = positionY;
        this.active = active;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int[] getPosition() {
        return position;
    }

    public boolean setPosition(int[] position) {
        boolean xInBounds = position[0] > 0 && position[0] < board.getWidth();
        boolean yInBounds = position[1] > 0 && position[1] < board.getHeight();
        if (xInBounds && yInBounds) {
            this.position = position;
        }
        return xInBounds && yInBounds;
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public boolean setPositionX(int x) {
        boolean xInBounds = x > 0 && x < board.getWidth();
        if (xInBounds) {
            this.position[0] = x;
        }
        return xInBounds;
    }

    public boolean setPositionY(int y) {
        boolean yInBounds = y > 0 && y < board.getHeight();
        if (yInBounds) {
            this.position[1] = y;
        }
        return yInBounds;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

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

    public void update() {
        board.placeInBoard(this);
    }
}
