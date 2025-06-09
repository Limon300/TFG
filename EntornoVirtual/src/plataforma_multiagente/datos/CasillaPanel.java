/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.awt.Color;

/**
 *
 * @author usuario
 */
public class CasillaPanel {
    int agentes;
    int poi;
    int obj;
    Color color;

    public CasillaPanel(Color color,int objetivos,int poi) {
        agentes = 0;
        this.color = color;
        this.poi = poi;
        this.obj = objetivos;
        
    }

    
    
    public int getAgentes() {
        return agentes;
    }

    public void incAgentes() {
        this.agentes++;
    }

    public void decAgentes() {
        this.agentes--;
    }
    
    public int getObj() {
        return obj;
    }

    public void incObj() {
        this.obj++;
    }

    public void decObj() {
        this.obj--;
    }
    
    public int getPOI() {
        return poi;
    }

    public void incPOI() {
        this.poi++;
    }

    public void decPOI() {
        this.poi--;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
