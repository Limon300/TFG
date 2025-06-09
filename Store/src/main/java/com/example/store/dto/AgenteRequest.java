/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import java.util.List;

/**
 *
 * @author usuario
 */
public class AgenteRequest {
    private String nombre;
    private String descripcion;
    private Long energia_max;
    private List<Long> habilidades;
    private List<Long> sensores;

    public AgenteRequest() {
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

    public List<Long> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Long> habilidades) {
        this.habilidades = habilidades;
    }

    public List<Long> getSensores() {
        return sensores;
    }

    public void setSensores(List<Long> sensores) {
        this.sensores = sensores;
    }  
    
}
