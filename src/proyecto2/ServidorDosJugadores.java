/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Adrian
 */
public class ServidorDosJugadores {
    
    int puerto;
    Jugador jugador1, jugador2;
    ArrayList<Jugador> listaJugadores = new ArrayList<Jugador>();
    Socket cliente1, cliente2;
    ArrayList<Socket> listaSockets = new ArrayList<Socket>();
    ThreadDosJugadores user1, user2;
    boolean juegoActivo = true;
    Color colorArray[] = {Color.red, Color.yellow, Color.blue, Color.green, Color.pink, Color.white};
    
    //public ArrayList<threadServidor> hilosserver;
    
    public ServidorDosJugadores(int puerto) {
        this.puerto = puerto;
    }
    
    // este es de pruebas
    public ServidorDosJugadores(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;
    }
    
    public void runServer() {
        try {
            //crea el socket servidor para aceptar dos conexiones
            ServerSocket serv = new ServerSocket(8081);
            System.out.println(".::Servidor Activo");
            System.out.println(".::Esperando dos usuarios");
            
            // espera primer cliente
            cliente1 = serv.accept();
            System.out.println(".::Primer cliente aceptado");
            user1 = new ThreadDosJugadores(cliente1, this, 1, jugador1.getNombre());
            user1.start();
            
            // espera segundo cliente
            cliente2 = serv.accept();
            System.out.println(".::Segundo cliente aceptado");
            user2 = new ThreadDosJugadores(cliente2, this, 2, jugador2.getNombre());
            user2.start();
            
            // 
            user1.enemigo = user2;
            user2.enemigo = user1;

            while (juegoActivo) {
                
            }
        } catch (IOException ex) {
            System.out.println("ERROR EN EL SERVIDOR!");
        }
    }
    
    public static void main(String[] args) {
        Jugador j1 = new Jugador("Adrian", 1, new JButton("Adrian"));
        Jugador j2 = new Jugador("Tomas", 2, new JButton("Tomas"));
        ServidorDosJugadores sv = new ServidorDosJugadores(j1, j2);
        sv.runServer();
    }
}
