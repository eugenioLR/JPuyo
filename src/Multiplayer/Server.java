package Multiplayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DuoGame.GameLoop;
import java.io.*;
import java.net.*;

/**
 *
 * @author eugeniolr
 */
public class Server extends Thread {

    public ServerSocket server;
    public Socket connection;
    public DataOutputStream output;
    public DataInputStream input;
    public GameLoop game;

    @Override
    public void run() {

        boolean running = true;
        int mode;
        try {
            //start server
            server = new ServerSocket(5000);
            server.bind(new InetSocketAddress("127.0.0.0", 5000));
            connection = server.accept();
            output = new DataOutputStream(connection.getOutputStream());
            input = new DataInputStream(connection.getInputStream());
        } catch (IOException ex) {
        }
    }
}
