/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author diemo
 */
public class Jugador {
    private String nombre;
    private int index;
    public JButton refButton;
    private int casillaActual = 1;
    private boolean activoTubo = false;
    private boolean esGanador = false;
    
    private Socket socket;
    private final int puerto = 8081;

    public Jugador(String nombre, int index, JButton refButton) {
        this.nombre = nombre;
        this.index = index;
        this.refButton = refButton;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public JButton getRefButton() {
        return refButton;
    }

    public void setRefButton(JButton refButton) {
        this.refButton = refButton;
    }

    public int getCasillaActual() {
        return casillaActual;
    }

    public void setCasillaActual(int casillaActual) {
        this.casillaActual = casillaActual;
    }
    
    public void setActivoTubo(boolean tubo) {
        this.activoTubo = tubo;
    }
    
    public boolean getActivoTubo() {
        return this.activoTubo;
    }

    public boolean isEsGanador() {
        return esGanador;
    }

    public void setEsGanador(boolean esGanador) {
        this.esGanador = esGanador;
    }
    
    
    public void avanzarCasillaActual(int avance) {
        this.casillaActual += avance;
        
        // para devolverse si no llegó al final exacto
        if (casillaActual > 27) {
            casillaActual = 28 - (casillaActual - 28);
            // esto hace que se devuelva las X casillas restantes del tiro de dado
        }
        
    }
}
