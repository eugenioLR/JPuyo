/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import DuoGame.BlockDuo;
import DuoGame.GameLoop;
import java.util.*;

/**
 *
 * @author Eugenio
 */
public class Board {

    private final Block board[][];
    private int width, height;
    private final Queue<BlockDuo> sequence;

    /**
     * Constructor for the class Board
     * @param width
     * @param height
     */
    public Board(int width, int height) {
        this.board = new Block[height][width];
        this.width = width;
        this.height = height;
        sequence = new LinkedList<>();
    }
    
    /**
     * Constructor for the class Board
     * @param width
     * @param height
     * @param sequence
     */
    public Board(int width, int height, Queue<BlockDuo> sequence) {
        this.board = new Block[height][width];
        this.width = width;
        this.height = height;
        this.sequence = sequence;
    }

    /**
     *
     * @return
     */
    public Block[][] getBoard() {
        return board;
    }

    /**
     *
     * @return
     */
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
     * @param x
     * @param y
     * @return
     */
    public Block getBlockAt(int x, int y) {
        if (y < height) {
            return board[y][x];
        } else {
            return board[y - 1][x];
        }
    }

    /**
     *
     * @param pos
     * @return
     */
    public Block getBlockAt(int pos[]) {
        if ((0 <= pos[1] && pos[1] < height) && (0 <= pos[0] && pos[0] < width)) {
            return board[pos[1]][pos[0]];
        } else {
            return null;
        }
    }

    public Queue<BlockDuo> getSequence(){
        return this.sequence;
    }
    
    public BlockDuo nextBlock(){
        return this.sequence.peek();
    }
    
    public boolean isEmpty(){
        boolean empty = true;
        for(int i = 0; i < this.getBoard().length && empty; i++){
            for(int j = 0; j < this.getBoard()[i].length && empty; j++){
                empty = this.getBlockAt(j, i) == null;
            }
        }
        return empty;
    }
    
    /**
     *
     * @param block
     */
    public void placeInBoard(Block block) {
        int y = block.getPositionY();
        int x = block.getPositionX();

        if (0 <= y && y < height) {
            board[y][x] = block;
        } else if (y >= height) {
            block.setPositionY(y - 1);
            this.placeInBoard(block);
        } else {
            block.setPositionY(y + 1);
            this.placeInBoard(block);
        }
    }
    
    public void placeInBoard(BlockDuo duo) {
        int y = duo.getPivot().getPositionY();
        int x = duo.getPivot().getPositionX();

        if (1 <= y && y < height-1) {
            board[y][x] = duo.getPivot();
            board[y-1][x] = duo.getExtension();
        } else if (y >= height) {
            duo.getPivot().setPositionY(y - 1);
            duo.getExtension().setPositionY(y - 1);
            this.placeInBoard(duo);
        } else {
            duo.getPivot().setPositionY(y + 1);
            duo.getExtension().setPositionY(y + 1);
            this.placeInBoard(duo);
        }
    }

    /**
     * removes a block from a position
     * @param pos
     */
    public void clearBlock(int pos[]) {
        board[pos[1]][pos[0]] = null;
    }

    /**
     * removes a block from a position
     * @param x
     * @param y
     */
    public void clearBlock(int x, int y) {
        board[y][x] = null;
    }

    
    /**
     * 
     */
    public void clearBoard(){
        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                clearBlock(i,j);
            }
        }
    }
    
    /**
     *
     * @param row
     * @param color
     * @return
     */
    public Block spawnBlock(int row, char color) {
        board[0][row] = new Block(color, row, 0);
        board[0][row].setBoard(this);
        return board[0][row];
    }
    
    /**
     *
     * @param column
     * @param row
     * @param color
     * @param active
     * @return
     */
    public Block spawnBlock(int column, int row, char color, boolean active) {
        board[column][row] = new Block(color, row, column, active);
        board[column][row].setBoard(this);
        return board[column][row];
    }
    
    /**
     *
     * @param row
     * @return
     */
    public BlockDuo spawnBlockDuo(int row) {
        BlockDuo duo = new BlockDuo(row, 0);
        duo.setBoard(this);
        duo.getPivot().setBoard(this);
        duo.getPivot().update();
        duo.getExtension().setBoard(this);
        duo.getExtension().update();
        return duo;
    }
    
    /**
     *
     * @param row
     * @param column
     * @param color
     * @return
     */
    public Block spawnBlockDuo(int row, int column, char color) {
        board[column][row] = new Block(color, row, column);
        board[column][row].setBoard(this);
        return board[column][row];
    }

    /**
     * fills the board with random blocks
     */
    public void randomFill() {
        char color;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                color = GameLoop.getCOLORS().get(GameLoop.randInt(0, GameLoop.getCOLORS().size() - 1));
                board[i][j] = new Block(color, i, j);
            }
        }
    }

    /**
     * checks for a chain and removes it
     * @return
     */
    public long checkChain() {
        ArrayList<int[]> chain;
        int score = 0;
        boolean chainFound = false;
        for (int i = 0; i < width && !chainFound; i++) {
            for (int j = 0; j < height && !chainFound; j++) {
                chain = sameColorChain(getBlockAt(i, j));
                if (chain.size() > 3) {
                    for (int[] blockPos : chain) {
                        this.clearBlock(blockPos);
                        score += 25 * chain.size(); //for a 4 chain each one adds 100
                    }
                    removeClearBlocks(chain);
                    chainFound = true;
                }
                chain.clear();
            }
        }
        return (long) score;
    }
    
    /**
     * removes the clear blocks near a chain
     * @param chain
     */
    private void removeClearBlocks(ArrayList<int[]> chain){
        int[] checkingPos = {0,0};
        int[][] offsetToCheck =  
        {{0,1},{1,0},{0,-1},{-1,0}};
        for(int[] pos : chain){
            for(int[] offset: offsetToCheck){
                checkingPos[0] = pos[0] + offset[0];
                checkingPos[1] = pos[1] + offset[1];
                if(checkingPos[0] >= 0 && checkingPos[0] < this.width && checkingPos[1] >= 0 && checkingPos[1] < this.height){
                    if(this.getBlockAt(checkingPos) != null && this.getBlockAt(checkingPos).getColor() == 'X'){
                        this.clearBlock(checkingPos);
                    }
                }
            }
        }
        
    }

    /**
     * recursively checks for a chain on the board 
     * @param block
     * @return
     */
    private ArrayList<int[]> sameColorChain(Block block) {
        ArrayList<int[]> posChecked = new ArrayList<>();
        if (block != null && block.getColor() != 'X') {
            return sameColorChain(block, posChecked);
        } else {
            return posChecked;
        }
    }

    /**
     * recursively checks for a chain on the board 
     * @param block
     * @param posChecked
     * @return
     */
    private ArrayList<int[]> sameColorChain(Block block, ArrayList<int[]> posChecked) {
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
                    sameColorChain(checkingBlock, posChecked);
                }
            }
        }
        return posChecked;
    }

    /**
     *
     */
    public void update() {}

    /**
     * Displays the contents of the board on a terminal
     * [Just for debugging/testing purposes]
     */
    public void drawTerminal() {
        Block block;
        System.out.println();
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
