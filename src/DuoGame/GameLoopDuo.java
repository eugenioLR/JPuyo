/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.*;
import SimpleGame.GameWindow;
import SimpleGame.KeyManager;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

/**
 *
 * @author ACER
 */
public class GameLoopDuo extends Thread{

    /**
     * @param args the command line arguments
     */
    private static final int WIDTH = 8, HEIGHT = 12;//, FRAMERATE = 60;
    private static int FRAMERATE;
    private long timer, score;
    private static ArrayList<Character> COLORS;
    private final BoardPanel gamePanel;
    private final JLabel pointsLabel, updateText, levelLabel;
    private final GameWindow gw;
    private final KeyManagerDuo keym;
    private int turnTicks, level;
    private final int initTurnTicks;
    
    /**
     *
     * @param gw
     */
    public GameLoopDuo(GameWindow gw){
        this.gw = gw;
        this.gamePanel = gw.getBoardPanel();
        this.pointsLabel = gw.getPointsLabel();
        this.updateText = gw.getupdateText();
        this.levelLabel = gw.getLevelLabel();
        this.keym = new KeyManagerDuo();
        initTurnTicks = 35;
        turnTicks = initTurnTicks;
        COLORS = new ArrayList<>();
        readActiveColors();
    }
       
    
    public static void readActiveColors(){
        String currentLine;
        String tokens[];
        try {
            File configFile = new File("jpuyo.conf");
            if(!configFile.exists()){
                createConfigFile();
            }
            Scanner scanner = new Scanner(configFile);
            while(scanner.hasNextLine()){
                currentLine = scanner.nextLine();
                //the line starts with 'COLORS:'
                if(Pattern.compile("^COLORS:").matcher(currentLine).find()){
                    currentLine = currentLine.split(":")[1];
                    tokens = currentLine.split(",");
                    for(String token:tokens){
                        if(token.length() == 1){
                            COLORS.add(token.charAt(0));
                        }
                    }
                }
            }
        } catch (IOException ex) {}
    }
    
    private static void createConfigFile(){
        try {
            FileWriter fw = new FileWriter("jpuyo.conf");
            fw.write("COLORS:B,G,Y,O,R,P,X");
            fw.close();
        } catch (IOException ex) {
            //COLORS = {'B', 'G', 'Y', 'O', 'R', 'P', 'X'};
        }
    }
    
    /**
     *
     */
    @Override
    public void run(){
        try {
            FRAMERATE = 60;
            gw.addKeyListener(keym);
            level = 0;
            duoBlockGame();
        } catch (InterruptedException | IOException ex) {}
    }

    /**
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void duoBlockGame() throws InterruptedException, IOException {
        Board board = new Board(WIDTH, HEIGHT);
        BlockDuo currentBlockDuo = null;
        Block checkingBlock;
        long auxScore;
        boolean lose = false;
        gamePanel.setBoard(board);
        for (timer = 0; !lose; timer++) {
            keym.activateTurn();
            sleep(1);
            keym.deactivateTurn();
            if (timer % (1000 / FRAMERATE) == 0) {
                if (((timer) % ((1000 / (FRAMERATE))*turnTicks) ) == 0) {
                    score++;
                    if(score/2000 + 4 < initTurnTicks){
                        turnTicks = initTurnTicks - (int) (score/2000);
                        level = (int) score/2000;
                    }else{
                        turnTicks = 4;
                        level = 4 + initTurnTicks;
                    }
                    
                    if(Integer.valueOf(levelLabel.getText().split(":")[1]) != level){
                        levelLabel.setText("Level:" + level);
                    }
                    pointsLabel.setText("\nScore:" + score);
                    
                    if (currentBlockDuo != null) {
                        currentBlockDuo.fall();
                    }
                    
                    if (currentBlockDuo == null || !currentBlockDuo.getPivot().isActive()) {
                        lose = firstRowEmpty(board.getBoard()[0]);
                        currentBlockDuo = board.spawnBlockDuo(WIDTH / 2);
                        keym.setCurrentBlock(currentBlockDuo);
                        
                        while ((auxScore = board.checkChain()) > 0) {
                            for (int i = HEIGHT - 1; i > 0; i--) {
                                for (int j = 0; j < WIDTH; j++) {
                                    checkingBlock = board.getBoard()[i][j];
                                    if (checkingBlock != null && checkingBlock != currentBlockDuo.getPivot()) {
                                        checkingBlock.drop();
                                    }
                                }
                            }
                            score += auxScore;
                            updateText.setText("CHAIN!");
                            gamePanel.setBoard(board);
                            gamePanel.repaint();
                            gw.repaint();
                            sleep(500);
                            updateText.setText("");
                            gamePanel.repaint();
                        }
                    }
                }
                gamePanel.setBoard(board);
                gamePanel.repaint();
                gw.repaint();
            }
        }
        updateText.setText("YOU LOSE.");
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
     * @param firstRow
     * @return
     */
    public static boolean firstRowEmpty(Block firstRow[]) {
        boolean found = false;
        for (int i = 0; i < firstRow.length && !found; i++) {
            found = firstRow[i] != null;
        }
        return found;
    }

    /**
     *
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
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
        GameLoopDuo.FRAMERATE = FRAMERATE;
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
}
