/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import JPuyo.BoardPanel;
import JPuyo.Images;
import SimpleGame.*;

/**
 *
 * @author eugeniolr
 */
public class GameWindowDuo extends GameWindow{
    
    @Override
    public void prepareGame(){
        new Images();
        this.boardPanel = new BoardPanel(8, 12);
        GameLoopDuo gl = new GameLoopDuo(this);
        this.add(boardPanel);
        gl.start();
    }
    
}
