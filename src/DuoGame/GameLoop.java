/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.*;
import challengeMode.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

/**
 *
 * @author ACER
 */
public class GameLoop extends Thread {

    private static final int WIDTH = 8, HEIGHT = 12;
    private static int FRAMERATE;
    private long timer, score;
    private static ArrayList<Character> COLORS;
    private final BoardPanel gamePanel;
    private final JLabel pointsLabel, updateText, levelLabel;
    private final GameWindow gw;
    private final KeyManager keym;
    private int turnTicks, level;
    private final int initTurnTicks;
    private final int mode;

    /**
     * Constuctor for GameLoop given a game window
     *
     * @param gw
     */
    public GameLoop(GameWindow gw,int mode) {
        this.gw = gw;
        this.gamePanel = gw.getBoardPanel();
        this.pointsLabel = gw.getPointsLabel();
        this.updateText = gw.getupdateText();
        this.levelLabel = gw.getLevelLabel();
        this.keym = new KeyManager();
        initTurnTicks = 35;
        turnTicks = initTurnTicks;
        COLORS = new ArrayList<>();
        readActiveColors();
        this.mode = mode;
    }
    
    /**
     *
     * @return
     */
    public static ArrayList<Character> getCOLORS() {
        return COLORS;
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
        GameLoop.FRAMERATE = FRAMERATE;
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
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max){
        return (int) Math.round(Math.random()*(max-min) + min);
    }
    
    /**
     * Reads the colors on the configuration file
     */
    public static void readActiveColors() {
        String currentLine;
        String tokens[];
        try {
            File configFile = new File("jpuyo.conf");
            if (!configFile.exists()) {
                createConfigFile();
            }
            Scanner scanner = new Scanner(configFile);
            while (scanner.hasNextLine()) {
                currentLine = scanner.nextLine();
                //the line starts with 'COLORS:'
                if (Pattern.compile("^COLORS:").matcher(currentLine).find()) {
                    currentLine = currentLine.split(":")[1];
                    tokens = currentLine.split(",");
                    for (String token : tokens) {
                        if (token.length() == 1) {
                            COLORS.add(token.charAt(0));
                        }
                    }
                }
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Creates the configuration file with all possible colors
     */
    private static void createConfigFile() {
        try {
            FileWriter fw = new FileWriter("jpuyo.conf");
            fw.write("COLORS:B,G,Y,O,R,P,X");
            fw.close();
        } catch (IOException ex) {
            //COLORS = {'B', 'G', 'Y', 'O', 'R', 'P', 'X'};
        }
    }

    /**
     * Executes the Thread and stats the game
     */
    @Override
    public void run() {
        try {
            FRAMERATE = 60;
            gw.addKeyListener(keym);
            level = 1;
            switch(mode){
                case 0:
                    mainGame();
                    break;
                case 1:
                    challengeMode();
                    break;
                default:
                    mainGame();
            }
        } catch (InterruptedException | IOException ex) {
        }
    }

    /**
     * Executes the game loop
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void mainGame() throws InterruptedException, IOException {
        Board board = new Board(WIDTH, HEIGHT);
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
                    keym.deactivateTurn();

                    score++;

                    //the level and turn ticks are calculated
                    if (score / 2000 + 4 < initTurnTicks) {
                        turnTicks = initTurnTicks - (int) (score / 2000);
                        level = (int) score / 2000;
                    } else {
                        turnTicks = 4;
                        level = 4 + initTurnTicks;
                    }

                    //if the level changes, it will be printed on the screen 
                    if (Integer.valueOf(levelLabel.getText().split(":")[1]) != level) {
                        levelLabel.setText("Level:" + level);
                    }
                    pointsLabel.setText("\nScore:" + score);

                    if (currentBlockDuo != null) {
                        currentBlockDuo.fall();
                        if (!currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                            currentBlockDuo.drop();
                        }
                    }

                    if (currentBlockDuo == null || !currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                        lose = !firstRowEmpty(board.getBoard());
                        currentBlockDuo = board.spawnBlockDuo(WIDTH / 2);
                        keym.setCurrentBlock(currentBlockDuo);
                        //will clear all the chains on the board one by one
                        nChains = 0;
                        while ((auxScore = board.checkChain()) > 0) {
                            nChains++;
                            for (int i = HEIGHT - 1; i > 0; i--) {
                                for (int j = 0; j < WIDTH; j++) {
                                    checkingBlock = board.getBoard()[i][j];
                                    if (checkingBlock != null && checkingBlock != currentBlockDuo.getPivot()) {
                                        checkingBlock.drop();
                                    }
                                }
                            }

                            //each consecutive chain will multiply the score obtained
                            score += auxScore * nChains;

                            //it will be shown how many chains the player has made as well as the score
                            if (nChains < 2) {
                                updateText.setText("CHAIN!");
                            } else {
                                updateText.setText("CHAIN x" + nChains + "!");
                            }
                            pointsLabel.setText("\nScore:" + score);

                            //the state of the board once a chain is cleared is shown
                            displayBoard(board);

                            //some time will be given to the player to see the state of the board
                            sleep(750);
                            updateText.setText("");
                        }
                    }
                    //the turn has ended, the player can now control the blocks
                    keym.activateTurn();
                }
                displayBoard(board);
            }
        }
        
        updateText.setText("YOU LOSE.");
        
        //once the player loses, a popup will ask him if he wants to restart
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "You Lost.\nRestart?", "Info", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            updateText.setText("");
            this.mainGame();
        } else {
            System.exit(0);
        }
    }
    
    public void challengeMode() throws InterruptedException, IOException{
        BlockDuo currentBlockDuo = null;
        Block checkingBlock;
        long auxScore;
        boolean lose = false, win = false;
        int nChains;
        
        while(!lose){
            level++;
            BoardSeq board = ChallengeReader.readChallenge((int)(Math.random()*2));
            gamePanel.setBoard(board);
            lose = false;
            win = false;
            for (timer = 0; !lose && !win; timer++) {
                //makes a millisecond passs
                sleep(1);
                //draws the screen at the framerate given
                if (timer % (1000 / FRAMERATE) == 0) {

                    //the block falls after a given number of frames have passed
                    if (((timer) % ((1000 / (FRAMERATE)) * turnTicks)) == 0) {

                        //no input can be recieved until the end of the turn
                        keym.deactivateTurn();

                        score++;

                        //the level and turn ticks are calculated
                        if (score / 2000 + 4 < initTurnTicks) {
                            turnTicks = initTurnTicks - (int) (score / 2000);
                        } else {
                            turnTicks = 4;
                        }
                        pointsLabel.setText("\nScore:" + score);

                        if (currentBlockDuo != null) {
                            currentBlockDuo.fall();
                            if (!currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                                currentBlockDuo.drop();
                            }
                        }

                        if (currentBlockDuo == null || !currentBlockDuo.getPivot().isActive() || !currentBlockDuo.getExtension().isActive()) {
                            //will clear all the chains on the board one by one
                            nChains = 0;
                            while ((auxScore = board.checkChain()) > 0) {
                                nChains++;
                                for (int i = HEIGHT - 1; i > 0; i--) {
                                    for (int j = 0; j < WIDTH; j++) {
                                        checkingBlock = board.getBoard()[i][j];
                                        if (checkingBlock != null && checkingBlock != currentBlockDuo.getPivot()) {
                                            checkingBlock.drop();
                                        }
                                    }
                                }

                                //each consecutive chain will multiply the score obtained
                                score += auxScore * nChains;

                                //it will be shown how many chains the player has made as well as the score
                                if (nChains < 2) {
                                    updateText.setText("CHAIN!");
                                } else {
                                    updateText.setText("CHAIN x" + nChains + "!");
                                }
                                pointsLabel.setText("\nScore:" + score);

                                //the state of the board once a chain is cleared is shown
                                displayBoard(board);

                                //some time will be given to the player to see the state of the board
                                sleep(750);
                                updateText.setText("");
                            }
                            
                            win = board.isEmpty();
                            lose = !firstRowEmpty(board.getBoard()) || !(board.getSequence().size() > 0);
                            if(board.getSequence().size() > 0){
                                currentBlockDuo = board.getSequence().remove(0);
                                currentBlockDuo.setBoard(board);
                                board.placeInBoard(currentBlockDuo);
                                keym.setCurrentBlock(currentBlockDuo);
                            }
                        }
                        //the turn has ended, the player can now control the blocks
                        keym.activateTurn();
                    }
                    displayBoard(board);
                }
            }
            if(win){
                updateText.setText("YOU WIN!!");
                levelLabel.setText("Level:" + level);
            }else{
                updateText.setText("YOU LOSE.");
            }

            //once the player loses, a popup will ask him if he wants to restart
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Continue?", "Info", dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
                updateText.setText("");
            } else {
                System.exit(0);
            }
            currentBlockDuo = null;
        }
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

    public void oponentAtack(int oponentScore) {
    }
}
