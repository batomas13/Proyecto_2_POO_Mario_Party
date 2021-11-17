/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Adrian
 */
public class Servidor {
    
    private final int puerto = 2555;
    private boolean esperando, empezar;
    private ServerSocket servidor;
    // se hace una lista con jugadores
    private ArrayList<Socket> clientes = new ArrayList<Socket>();
    
    public Servidor() {
        
        esperando = true;
        empezar = false;
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
        } catch (IOException ex) {
            //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No se pudo acceder al puerto:" + puerto);
            System.exit(1);
        }
    }
    
    private void iniciarJugadores() {
        while(esperando) {
            
        }
    }
}
