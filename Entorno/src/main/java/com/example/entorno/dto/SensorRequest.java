/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

/**
 *
 * @author usuario
 */
public class SensorRequest {
    private Long id_agente;
    private Long id_sensor;

    public SensorRequest() {
    }

    public SensorRequest(Long id_agente, Long id_sensor) {
        this.id_agente = id_agente;
        this.id_sensor = id_sensor;
    }
    
    

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public Long getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(Long id_sensor) {
        this.id_sensor = id_sensor;
    }


    
}
