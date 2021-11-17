/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.*;
import java.net.*;

/**
 *
 * @author Adrian
 */
public class ThreadDosJugadores extends Thread {
    
    Socket cliente = null;
    DataInputStream entrada=null;//Para leer comunicacion
    DataOutputStream salida=null;//Para enviar comunicacion	
    public String nameUser; //Para el nombre del usuario de esta conexion
    ServidorDosJugadores servidor;// referencia al servidor
    public ThreadDosJugadores enemigo = null;
    public int numeroJugador;
    
    public ThreadDosJugadores (Socket cliente, ServidorDosJugadores serv, int indx, String nombre) {
        this.cliente = cliente;
        this.servidor = serv;
        this.numeroJugador = indx;
        this.nameUser = nombre;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    
    
    
    @Override
    public void run() {
        
        try {
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
            
            System.out.println("Ingreso al thread el jugador " + numeroJugador + ": " + nameUser);
        } catch (IOException e) {
            System.out.println("Hubo un problema con inicializar los data streams");
            e.printStackTrace();
        }
        
        int opcion = 0;
        
        while (true) {
            try {
                opcion = entrada.readInt();
                
                switch(opcion) {
                    case 1:
                        //envio de coordenada
                        // LEYO OPCION 1
                        // LEE LAS COORDENADAS QUE ENVIO ESTE CLIENTE
                        // Luego las pasa al enemigo para que marque su tablero
                        int columna = entrada.readInt();//Lee coordenada
                        int fila = entrada.readInt();//Lee coordenada fila
                        System.out.println("Recibido " + columna + "," + fila);
                        // ENVIA LA COORDENADA AL ENEMIGO
                        enemigo.salida.writeInt(1);// Opcion 1 al hilo cliente del enemigo
                        enemigo.salida.writeInt(columna);// envia columna
                        enemigo.salida.writeInt(fila);// envia fila
                        System.out.println("Op1: lee col,fil, envia al enemigo, 1, col, fila: "+columna+" , "+fila );
                        break;
                    case 2:
                        //le envia el status, que es el numero de jugador y el nombre enemigo
                        salida.writeInt(3);
                        salida.writeInt(numeroJugador);
                        if (enemigo != null) {
                            salida.writeUTF(enemigo.nameUser);
                        }
                        else {
                            salida.writeUTF("");
                        }
                        System.out.println("3. Op3: envia 3 y numeroJugador y enemigo: "+ numeroJugador);
                        break;
                    // case 3 se terminó el juego, cierre el serv
                    case 3:
                        servidor.juegoActivo = false;
                        break;
                    default:
                        break;
                }
                
            } catch (IOException ex) {
                System.out.println("Hubo un error al leer el codigo de operacion");
                break;
            }
        }
        
        System.out.println("Se removio un usuario");
        try {
            System.out.println("Se desconecto un usuario: " + nameUser);
            cliente.close();
        } catch (Exception ex) {
            System.out.println("No se pudo cerrar el socket");
        }
    }
    
    public void enviaUser() {
        if (enemigo != null) {
            try {
                enemigo.salida.writeInt(2);//escribe opcion de agregar 2
                enemigo.salida.writeUTF(this.getNameUser());//escribe nombre	
                System.out.println("2. Envia 2 y username "+ "2" + getNameUser());
            } catch (IOException ex) {
                System.out.println("Hubo un error al enviar el nombre al enemigo");
            }
        }
    }
}
