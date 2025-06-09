/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import java.sql.Timestamp;

/**
 *
 * @author usuario
 */
public class LogRequest {
    private Timestamp fecha_min;
    private Timestamp fecha_max;

    public LogRequest() {
    }

    public Timestamp getFecha_min() {
        return fecha_min;
    }

    public void setFecha_min(Timestamp fecha_min) {
        this.fecha_min = fecha_min;
    }

    public Timestamp getFecha_max() {
        return fecha_max;
    }

    public void setFecha_max(Timestamp fecha_max) {
        this.fecha_max = fecha_max;
    }

    

    
    
}
