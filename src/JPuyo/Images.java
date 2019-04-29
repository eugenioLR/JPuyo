/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPuyo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ACER
 */
public class Images {
    public static BufferedImage blue;
    public static BufferedImage purple;
    public static BufferedImage red;
    public static BufferedImage orange;
    public static BufferedImage yellow;
    public static BufferedImage green;
    public static BufferedImage clear;
    public static BufferedImage wall;
    public static BufferedImage wall2;
    public static BufferedImage gap;
    public static BufferedImage nothing;
    

    public Images() {
        try {
            blue = ImageIO.read(getClass().getClassLoader().getResource("res/Blue.png"));
            clear = ImageIO.read(getClass().getClassLoader().getResource("res/Clear.png"));
            gap = ImageIO.read(getClass().getClassLoader().getResource("res/Gap.png"));
            green = ImageIO.read(getClass().getClassLoader().getResource("res/Green.png"));
            orange = ImageIO.read(getClass().getClassLoader().getResource("res/Orange.png"));
            purple = ImageIO.read(getClass().getClassLoader().getResource("res/Purple.png"));
            red = ImageIO.read(getClass().getClassLoader().getResource("res/Red.png"));
            wall = ImageIO.read(getClass().getClassLoader().getResource("res/Wall.png"));
            wall2 = ImageIO.read(getClass().getClassLoader().getResource("res/Wall2.png"));
            yellow = ImageIO.read(getClass().getClassLoader().getResource("res/Yellow.png"));
            nothing = ImageIO.read(getClass().getClassLoader().getResource("res/Null.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
