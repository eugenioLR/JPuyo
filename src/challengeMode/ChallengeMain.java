/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challengeMode;

import JPuyo.Board;
import java.io.FileNotFoundException;

/**
 *
 * @author eugeniolr
 */
public class ChallengeMain {

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String args[]) throws FileNotFoundException{
        Board b = ChallengeReader.readChallenge(1);
        if(b != null){
            b.drawTerminal();
        }else{
            System.out.println("no board");
        }
    }
}
