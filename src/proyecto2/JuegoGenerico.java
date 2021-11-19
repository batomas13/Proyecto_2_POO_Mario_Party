/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import javax.swing.JFrame;

/**
 *
 * @author Adrian
 */
public abstract class JuegoGenerico extends JFrame{
    public int numeroJugador;
    public abstract void setEnemigo(String nombreEnemigo);
}
