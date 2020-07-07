/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challengeMode;
import DuoGame.BlockDuo;
import JPuyo.Board;
import java.io.*;
import java.util.*;
import org.json.*;
import java.util.regex.Pattern;

/**
 *
 * @author eugeniolr
 */
public class ChallengeReader {

    /**
     *
     * @param chId
     * @return
     * @throws FileNotFoundException
     */
    public static Board readChallenge(int chId) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("challenges/challenge" + chId + ".json"));
        String jsonFile = scanner.useDelimiter("\\A").next();
        JSONObject obj = new JSONObject(jsonFile);
        
        int h = obj.getInt("height");
        int w = obj.getInt("width");
        Queue<BlockDuo> seq = new LinkedList<>();
        JSONArray puyos = obj.getJSONArray("puyos");
        for(int i = 0; i < puyos.length(); i++){
            seq.add(new BlockDuo(w/2, 0, puyos.getString(i).charAt(0), puyos.getString(i).charAt(1)));
        }
        
        Board board = new Board(w, h, seq);
        JSONArray boardColor = obj.getJSONArray("board");
        for(int i = 0; i < boardColor.length(); i++){
            for(int j = 0; j < boardColor.getString(i).length(); j++){
                if(boardColor.getString(i).charAt(j) != '_'){
                    board.spawnBlock(i, j, boardColor.getString(i).charAt(j), false);
                }
            }
        }
        
        return board;
    }
    
    /**
     *
     * @param pathFile
     * @return
     * @throws FileNotFoundException
     */
    public static Board readChallenge(String pathFile) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File("challenges/" + pathFile));
        String jsonFile = scanner.useDelimiter("\\A").next();
        JSONObject obj = new JSONObject(jsonFile);
        
        int h = obj.getInt("height");
        int w = obj.getInt("width");
        Queue<BlockDuo> seq = new LinkedList<>();
        JSONArray puyos = obj.getJSONArray("puyos");
        for(int i = 0; i < puyos.length(); i++){
            seq.add(new BlockDuo(w/2, 0, puyos.getString(i).charAt(0), puyos.getString(i).charAt(1)));
        }
        
        Board board = new Board(w, h, seq);
        JSONArray boardColor = obj.getJSONArray("board");
        for(int i = 0; i < boardColor.length(); i++){
            for(int j = 0; j < boardColor.getString(i).length(); j++){
                if(boardColor.getString(i).charAt(j) != '_'){
                    board.spawnBlock(i, j, boardColor.getString(i).charAt(j), false);
                }
            }
        }
        
        return board;
    }
    
    /**
     *
     * @return
     * @throws NoChallengesException
     */
    public static int challengeAmount() throws NoChallengesException{
        int counter = 0;
        if(!(new File("challenges/")).exists()){
            throw new NoChallengesException();
        }
        File[] challenges = (new File("challenges/")).listFiles();
        for (File challenge : challenges) {
            if (Pattern.compile("challenge[0-9]+.json").matcher(challenge.getName()).find()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     *
     * @return
     */
    public static String selectChallenge() {
        File[] challenges = (new File("challenges/")).listFiles();
        ArrayList<String> validChallenges = new ArrayList<>();
        for (File challenge : challenges) {
            if (Pattern.compile("challenge[0-9]+.json").matcher(challenge.getName()).find()) {
                validChallenges.add(challenge.getName());
            }
        }
        System.out.println(validChallenges.get((int)(Math.random() * validChallenges.size())));
        return validChallenges.get((int)(Math.random() * validChallenges.size()));
    }
}
