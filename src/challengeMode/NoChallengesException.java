/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challengeMode;

/**
 *
 * @author eugeniolr
 */
public class NoChallengesException extends Exception{

    /**
     * Constructor for the exception NoChallengesException 
     */
    public NoChallengesException() {
        super("No challenges Found");
    }
    
    /**
     *
     * @param string
     */
    public NoChallengesException(String string) {
        super(string);
    }
}