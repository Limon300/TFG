/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class SensorResponse {

    private List<Casilla> casillas;
    private Long valor;

    public SensorResponse() {
        casillas = new ArrayList();
    }

    public SensorResponse(List<Casilla> casillas, Long valor) {
        this.casillas = casillas;
        this.valor = valor;
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
    
}
