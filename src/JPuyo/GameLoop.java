/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author ACER
 */
public class GameLoop {

    /**
     * @param args the command line arguments
     */
    private static final int WIDTH = 8, HEIGHT = 12;//, FRAMERATE = 60;
    private static int FRAMERATE;
    private static long timer, score;
    private static final char COLORS[] = {'B', 'G', 'Y', 'O', 'R', 'P'};//, 'X'};
    private static JFrame mainFrame = new JFrame("Puyo-Puyo");
    private static DisplayBoard gamePanel = new DisplayBoard(WIDTH, HEIGHT);
    private static KeyManager keym = new KeyManager();
    //private static StartMenu menu = new StartMenu();

    public static void main(String[] args) throws InterruptedException {
        try {
            if (args.length > 0) {
                FRAMERATE = Integer.parseInt(args[0]);
            } else {
                FRAMERATE = 30;
            }
            new Images();
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setSize(20 * WIDTH + 16 * 16, 20 * HEIGHT + 7);
            mainFrame.setVisible(true);
            mainFrame.getContentPane().add(gamePanel);

            mainFrame.addKeyListener(keym);
            //menu.setVisible(true);
            //mainFrame.getContentPane().add(gamePanel);
            singleBlockGame();
            //dualBlockGame();
        } catch (IOException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NumberFormatException nfe){
            System.out.println(nfe);
            String[] empty = {};
            main(empty);
        }
    }

    public static void startMenu() {
    }

    public static void singleBlockGame() throws InterruptedException, IOException {
        Board board = new Board(WIDTH, HEIGHT);
        Block currentBlock = null;
        Block checkingBlock;
        int leftRightStick;
        long auxScore;
        boolean lose = false;

        gamePanel.setBoard(board);
        gamePanel.setVisible(true);
        for (timer = 0; !lose; timer++) {
            Thread.sleep(1);
            if (timer % (1000 / FRAMERATE) == 0) {

                if (((timer / 5) % (1000 / (FRAMERATE))) == 0) {
                    score++;

                    //System.out.print("\nScore:" + score);
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
            }
        }
        System.out.println("YOU LOSE, SCORE: " + score);
    }

    public static void dualBlockGame() {

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
}
