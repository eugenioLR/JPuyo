/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuoGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author eugeniolr
 */
public class Colors {
    private final static ArrayList<Character> COLORS = new ArrayList<>();
    
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
        }
    }
    
    public static ArrayList<Character> getCOLORS() {
        return COLORS;
    }
}
