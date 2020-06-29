/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challengeMode;

import DuoGame.*;
import JPuyo.*;
import java.util.ArrayList;

/**
 *
 * @author eugeniolr
 */
public class BoardSeq extends Board{
    ArrayList<BlockDuo> sequence;
    
    public BoardSeq(int width, int height, ArrayList<BlockDuo> sequence) {
        super(width, height);
        this.sequence = sequence;
    }
    
    public ArrayList<BlockDuo> getSequence(){
        return this.sequence;
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
}
