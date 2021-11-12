/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2;

import javax.swing.JButton;

/**
 *
 * @author diemo
 */
public class Jugador {
    private String nombre;
    private int index;
    private JButton refButton;
    private int casillaActual = 1;

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
    
    public void avanzarCasillaActual(int avance) {
        this.casillaActual += avance;
    }
    
    
    
}
