 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multiplayer;

import DuoGame.*;
import JPuyo.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.regex.*;
import javax.swing.*;
        
/**
 *
 * @author ACER
 */
public class GameMultiLoop extends Thread {

    private static final int WIDTH = 8, HEIGHT = 12;
    private static int FRAMERATE;
    private long timer, score;
    private Board board;
    private final BoardPanel gamePanel;
    private final GameMultiWindow gw;
    private final KeyManager keym;
    private int turnTicks;
    private final int initTurnTicks, player;
    private final Semaphore semaphore;

    /**
     * Constuctor for GameLoop given a game window
     *
     * @param gw
     * @param player
     */
    public GameMultiLoop(GameMultiWindow gw, int player) {
        this.gw = gw;
        this.gamePanel = gw.getBoardPanel().get(player);
        this.keym = new KeyManager(player);
        initTurnTicks = 35;
        turnTicks = initTurnTicks;
        semaphore = new Semaphore(1);
        this.player = player;
    }
    
    /**
     *
     * @return
     */
    public static int getFRAMERATE() {
        return FRAMERATE;
    }

    /**
     *
     * @param FRAMERATE
     */
    public static void setFRAMERATE(int FRAMERATE) {
        GameMultiLoop.FRAMERATE = FRAMERATE;
    }

    /**
     *
     * @return
     */
    public long getTimer() {
        return timer;
    }

    /**
     *
     * @param timer
     */
    public void setTimer(long timer) {
        this.timer = timer;
    }

    /**
     *
     * @return
     */
    public long getScore() {
        return score;
    }

    /**
     *
     * @param score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     *
     * @return
     */
    public int getPlayer() {
        return player;
    }
    
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max){
        return (int) Math.round(Math.random()*(max-min) + min);
    }
    
    /**
     * Executes the Thread and stats the game
     */
    @Override
    public void run() {
        try {
            FRAMERATE = 60;
            gw.getMainFrame().addKeyListener(keym);
            gw.addKeyListener(keym);
            multiplayerGame();
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }finally{
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Go back to the main menu?", "Back to menu", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                gw.exitToMenu();
            }else{
                System.exit(0);
            }
        }
    }

    /**
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void multiplayerGame() throws InterruptedException, IOException{
        this.board = new Board(WIDTH, HEIGHT);
        board.getSequence().add(new BlockDuo(WIDTH/2, 0));
        BlockDuo currentBlockDuo = null;
        Block checkingBlock;
        long auxScore;
        boolean lose = false;
        int nChains;
        gamePanel.setBoard(board);
        for (timer = 0; !lose; timer++) {
            //makes a millisecond passs
            sleep(1);

            //draws the screen at the framerate given
            if (timer % (1000 / FRAMERATE) == 0) {

                //the block falls after a given number of frames have passed
                if (((timer) % ((1000 / (FRAMERATE)) * turnTicks)) == 0) {
                //no input can be recieved until the end of the turn
                    semaphore.acquire();
                    
                    keym.deactivateTurn();

                    if (currentBlockDuo != null) {
                        currentBlockDuo.fall();
                        if (!currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                            currentBlockDuo.drop();
                        }
                    }

                    if (currentBlockDuo == null || !currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                        lose = !firstRowEmpty(board.getBoard());
                        board.getSequence().add(new BlockDuo(WIDTH/2, 0));
                        if(board.getSequence().size() > 0){
                            currentBlockDuo = board.getSequence().remove();
                            currentBlockDuo.setBoard(board);
                            board.placeInBoard(currentBlockDuo);
                            keym.setCurrentBlock(currentBlockDuo);
                        }
                        
                        keym.setCurrentBlock(currentBlockDuo);
                        //will clear all the chains on the board one by one
                        nChains = 0;
                        while ((auxScore = board.checkChain()) > 0) {
                            nChains++;
                            for (int i = HEIGHT - 1; i > 0; i--) {
                                for (int j = 0; j < WIDTH; j++) {
                                    checkingBlock = board.getBoard()[i][j];
                                    if (checkingBlock != null) {
                                        checkingBlock.drop();
                                    }
                                }
                            }
                            
                            score += auxScore * nChains;

                            //the state of the board once a chain is cleared is shown
                            displayBoard(board);

                            //some time will be given to the player to see the state of the board
                            sleep(750);
                            //updateText.setText("");
                        }
                        
                        if(score > 2500){
                            attack();
                            score = 0;
                        }
                    }
                    
                    
                    //the turn has ended, the player can now control the blocks
                    keym.activateTurn();
                    
                    semaphore.release();
                }
                displayBoard(board);
            }
        }
    }
    
    /**
     *
     * @param amount
     * @throws InterruptedException
     */
    public void oponentAttack(int amount) throws InterruptedException{
        semaphore.acquire();
        System.out.println("hey");
        
        ArrayList<Block> generatedBlocks = new ArrayList<>();
        
        for(int i = 0; i < amount; i++){
            generatedBlocks.add(board.spawnBlock(i/board.getWidth(), i%board.getWidth(), 'X', true));
        }
        
        displayBoard(board);
        sleep(500);
        
        for(Block b : generatedBlocks){
            b.drop();
        }
        System.out.println("you got destroyed :)");
        semaphore.release();
    }
    
    /**
     * sends clear blocks to the opponent
     */
    public void attack(){
        
    }
    
    /**
     * Displays the board on to the screen
     * @param board
     */
    private void displayBoard(Board board) {
        gamePanel.setBoard(board);
        gamePanel.repaint();
        gw.repaint();
    }

    /**
     * Checks if the first row of the board is empty
     * @param board
     * @return
     */
    public static boolean firstRowEmpty(Block board[][]) {
        boolean blockFound = false;
        for (int i = 0; i < board[0].length && !blockFound; i++) {
            blockFound = board[0][i] != null;
        }
        return !blockFound;
    }
}