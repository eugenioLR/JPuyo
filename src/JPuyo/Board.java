/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Eugenio
 */
public class Board {

    private Block board[][];
    private int width, height;

    public Board(int width, int height) {
        this.board = new Block[height][width];
        this.width = width;
        this.height = height;
    }

    public Block[][] getBoard() {
        return board;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Block getBlockAt(int x, int y) {
        if (y < height) {
            return board[y][x];
        } else {
            return board[y - 1][x];
        }
    }

    public Block getBlockAt(int pos[]) {
        if ((pos[1] >= 0 && pos[1] < height) && (pos[0] >= 0 && pos[0] < width)) {
            return board[pos[1]][pos[0]];
        } else {
            return null;
        }
    }

    public void placeInBoard(Block block) {
        int y = block.getPositionY();
        int x = block.getPositionX();

        if (y < height && y > -1) {
            board[y][x] = block;
        } else if (y >= height) {
            block.setPositionY(y - 1);
            this.placeInBoard(block);
        } else {
            block.setPositionY(y + 1);
            this.placeInBoard(block);
        }
    }

    public void clearBlock(int pos[]) {
        if (getBlockAt(pos) != null) {
            board[pos[1]][pos[0]] = null;
        }
    }

    public void clearBlock(int x, int y) {
        if (getBlockAt(x, y) != null) {
            board[y][x] = null;
        }
    }

    public Block spawnBlock(int row, char color) {
        board[0][row] = new Block(color, row, 0);
        board[0][row].setBoard(this);
        return board[0][row];
    }

    public Block spawnBlockDuo(int row) {
        BlockDuo duo = new BlockDuo(row, 0);
        board[0][row] = duo.getPivot();
        board[1][row] = duo.getExtension();
        board[0][row].setBoard(this);
        board[1][row].setBoard(this);
        return board[0][row];
    }

    public Block spawnBlockDuo(int row, int column, char color) {
        board[column][row] = new Block(color, row, column);
        board[column][row].setBoard(this);
        return board[column][row];
    }

    public void randomFill() {
        char color;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = GameLoop.getCOLORS()[GameLoop.randInt(0, GameLoop.getCOLORS().length - 1)];
                board[i][j] = new Block(color, i, j);
            }
        }
    }

    public long checkChain() {
        ArrayList<int[]> chain;
        int score = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                chain = sameColorChain(getBlockAt(i, j));
                if (chain.size() > 3) {
                    for (int[] blockPos : chain) {
                        this.clearBlock(blockPos);
                        score += 100;
                    }
                }
                chain.clear();
            }
        }
        return (long) score;
    }

    public ArrayList<int[]> sameColorChain(Block block) {
        ArrayList<int[]> posChecked = new ArrayList<>();

        if (block != null) {
            return sameColorChain(block, posChecked);
        } else {
            return posChecked;
        }
    }

    public ArrayList<int[]> sameColorChain(Block block, ArrayList<int[]> posChecked) {
        posChecked.add(block.getPosition());
        int[] blockPos = block.getPosition();
        int[][] posToCheck = 
        {{blockPos[0], blockPos[1] + 1}, {blockPos[0] + 1, blockPos[1]},
         {blockPos[0], blockPos[1] - 1}, {blockPos[0] - 1, blockPos[1]}};
        boolean alreadyIn = false;
        Block checkingBlock;

        for (int[] pos : posToCheck) {
            checkingBlock = this.getBlockAt(pos);
            if (checkingBlock != null && checkingBlock.getColor() == block.getColor()) {

                for (int[] arr : posChecked) {
                    if (alreadyIn = Arrays.equals(arr, pos)) {
                        break;
                    }
                }

                if (!alreadyIn) {
                    if (true) {
                        sameColorChain(checkingBlock, posChecked);
                    } else {
                    }
                }
            }
        }
        return posChecked;
    }

    public void update() {
    }

    public void drawTerminal() {
        Block block;

        System.out.println("");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                block = board[i][j];
                if (block == null) {
                    System.out.print("-");
                } else {
                    System.out.print(block.getColor());
                }
            }
            System.out.println("");
        }
        System.out.print("\n____________________\n");
    }

}
