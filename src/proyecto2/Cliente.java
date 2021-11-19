/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proyecto2;

import java.io.*;
import java.net.*;

/**
 *
 * @author Diego
 */
public class Cliente {
    public static String IP_SERVER = "localhost"; //IP del Servidor
    JuegoGenerico ventanaCliente; // Ventana del cliente
    DataInputStream entrada = null;//leer comunicacion
    DataOutputStream salida = null;//escribir comunicacion
    Socket cliente = null;//para la comunicacion
    
    Jugador jugadorCliente;
    String tipoVentana;
   
   /** Creates a new instance of Cliente
     * @param vent 
     * @param jugador 
     * @throws IOException */
    public Cliente(JuegoGenerico vent, Jugador jugador) throws IOException {
       this.ventanaCliente = vent;
       this.jugadorCliente = jugador;
    }
    
    public void conexion() throws IOException 
    {
        try {
            // se conecta con dos sockets al server, uno comunicacion otro msjes
            cliente = new Socket(Cliente.IP_SERVER, 8081);
            // inicializa las entradas-lectura y salidas-escritura
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
            // solicita el nombre del user
            //nomCliente = JOptionPane.showInputDialog("Introducir Nick :");
            //Lo coloca en la ventana
            ventanaCliente.setTitle(jugadorCliente.getNombre());
            // es lo primero que envia al server
            // el thread servidor esta pendiente de leer el nombre antes de entrar
            // al while para leer opciones
            //salida.writeUTF(nomCliente);
            System.out.println("1. Envia el nombre del cliente: " + jugadorCliente.getNombre());
      } catch (IOException e) {
         System.out.println("\tEl servidor no esta levantado");
         System.out.println("\t=============================");
      }
      // solo se le pasa entrada pues es solo para leer mensajes
      // el hiloCliente lee lo que el servidor le envia, opciones y como tiene referencia
      // a la ventana gato puede colocar en la pantalla cualquier cosa, como las
      //imagenes de X o O, llamar a metodo marcar, colocar el nombre de enemigo
      // o el suyo propio
      new threadCliente(entrada, ventanaCliente).start();
   }
   
   //GETTET AND SETTER
   public String getNombre()
   {
      return jugadorCliente.getNombre();
   }

    public void setVentanaCliente(JuegoGenerico ventanaCliente) {
        this.ventanaCliente = ventanaCliente;
    }
   
   
}
