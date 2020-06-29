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

/**
 *
 * @author eugeniolr
 */
public class ChallengeReader {
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
}
