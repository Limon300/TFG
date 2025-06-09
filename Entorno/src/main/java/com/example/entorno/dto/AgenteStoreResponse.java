/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import java.util.List;

/**
 *
 * @author usuario
 */
public class AgenteStoreResponse {
    private int id_agente;
    private String nombre;
    private String descripcion;
    private Long energia_max;
    private List<HabilidadResponse> habilidades;
    private List<SensorStoreResponse> sensores;

    public AgenteStoreResponse() {
    }

    public AgenteStoreResponse(int id_agente, String nombre, String descripcion, Long energia_max,
            List<HabilidadResponse> habilidades,List<SensorStoreResponse> sensores) {
        this.id_agente = id_agente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.energia_max = energia_max;
        this.habilidades = habilidades;
        this.sensores = sensores;
    }

    public int getId_agente() {
        return id_agente;
    }

    public void setId_agente(int id_agente) {
        this.id_agente = id_agente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEnergia_max() {
        return energia_max;
    }

    public void setEnergia_max(Long energia_max) {
        this.energia_max = energia_max;
    }

    public List<HabilidadResponse> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<HabilidadResponse> habilidades) {
        this.habilidades = habilidades;
    }

    public List<SensorStoreResponse> getSensores() {
        return sensores;
    }

    public void setSensores(List<SensorStoreResponse> sensores) {
        this.sensores = sensores;
    }

   
    
    
}
