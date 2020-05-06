/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGame;

import JPuyo.Block;
import JPuyo.Board;
import JPuyo.BoardPanel;
import SimpleGame.KeyManager;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author ACER
 */
public class GameLoop extends Thread{

    /**
     * @param args the command line arguments
     */
    private static final int WIDTH = 8, HEIGHT = 12;//, FRAMERATE = 60;
    private static int FRAMERATE;
    private long timer, score;
    private static final char COLORS[] = {'B', 'G', 'Y', 'O', 'R', 'P'};//, 'X'};
    private final BoardPanel gamePanel;
    private final JLabel pointsLabel;
    private final GameWindow gw;
    private final KeyManager keym;
    
    public GameLoop(GameWindow gw){
        this.gw = gw;
        this.gamePanel = gw.getBoardPanel();
        this.pointsLabel = gw.getPointsLabel();
        this.keym = new KeyManager();
    }
    
    @Override
    public void run(){
        try {
            FRAMERATE = 30;
            gw.addKeyListener(keym);
            singleBlockGame();
        } catch (InterruptedException | IOException ex) {}
    }


    public void singleBlockGame() throws InterruptedException, IOException {
        Board board = new Board(WIDTH, HEIGHT);
        Block currentBlock = null;
        Block checkingBlock;
        int leftRightStick;
        long auxScore;
        boolean lose = false;

        gamePanel.setBoard(board);
        for (timer = 0; !lose; timer++) {
            sleep(1);
            if (timer % (1000 / FRAMERATE) == 0) {

                if (((timer / 5) % (1000 / (FRAMERATE))) == 0) {
                    score++;

                    pointsLabel.setText("\nScore:" + score);
                    if (currentBlock != null) {
                        currentBlock.fall();
                    }
                    if (currentBlock == null || !currentBlock.isActive()) {
                        lose = firstRowEmpty(board.getBoard()[0]);
                        currentBlock = board.spawnBlock(WIDTH / 2, COLORS[randInt(0, COLORS.length - 1)]);
                        keym.setCurrentBlock(currentBlock);
                        while ((auxScore = board.checkChain()) > 0) {
                            for (int i = HEIGHT - 1; i > 0; i--) {
                                for (int j = 0; j < WIDTH; j++) {
                                    checkingBlock = board.getBoard()[i][j];
                                    if (checkingBlock != null && checkingBlock != currentBlock) {
                                        checkingBlock.drop();
                                        score += auxScore;
                                        auxScore = 0;
                                        gamePanel.setBoard(board);
                                        gamePanel.repaint();
                                    }
                                }
                            }
                            Thread.sleep(500);
                            System.out.println("CHAIN! SCORE:" + score);
                        }

                    }
                }
                gamePanel.setBoard(board);
                gamePanel.repaint();
                gw.repaint();
            }
        }
        System.out.println("YOU LOSE, SCORE: " + score);
    }


    public static char[] getCOLORS() {
        return COLORS;
    }

    public static boolean firstRowEmpty(Block firstRow[]) {
        boolean found = false;
        for (int i = 0; i < firstRow.length && !found; i++) {
            found = firstRow[i] != null;
        }
        return found;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int getFRAMERATE() {
        return FRAMERATE;
    }

    public static void setFRAMERATE(int FRAMERATE) {
        GameLoop.FRAMERATE = FRAMERATE;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
    
    
}
