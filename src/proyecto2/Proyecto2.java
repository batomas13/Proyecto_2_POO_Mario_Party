/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto2;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author tcoto
 */
public class Proyecto2 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //Tablero juego = new Tablero();
        //Juego_Who Juego = new Juego_Who();
        
        //Juego_gato Juego = new Juego_gato();
        //Juego_bombermario Juego = new Juego_bombermario();
        //Juego_Memory Juego = new Juego_Memory();
        Jugador Jugador1 = new Jugador("HOLA", 0, null);
        Jugador Jugador2 = new Jugador("ADIOS", 0, null);
        //Juego_Memory Juego = new Juego_Memory(Jugador1, Jugador2);
        ArrayList<Jugador> Jugadores = new ArrayList();
        Jugadores.add(Jugador1);
        Jugadores.add(Jugador2);
        Juego_Cards Juego = new Juego_Cards(Jugadores, Jugador1);
        //Juego_gato Juego = new Juego_gato (Jugador1, Jugador2);
        Juego.setVisible(true);
    }
    
}
