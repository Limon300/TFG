/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.AgenteModelo;
import com.example.store.models.HabilidadModelo;
import com.example.store.models.SensorModelo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class AgenteResponse {
    private int id_agente;
    private String nombre;
    private String descripcion;
    private Long energia_max;
    private List<HabilidadResponse> habilidades;
    private List<SensorResponse> sensores;

    public AgenteResponse() {
    }

    public AgenteResponse(int id_agente, String nombre, String descripcion, Long energia_max,
            List<HabilidadResponse> habilidades,List<SensorResponse> sensores) {
        this.id_agente = id_agente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.energia_max = energia_max;
        this.habilidades = habilidades;
        this.sensores = sensores;
    }

    public AgenteResponse(AgenteModelo get) {
        this.id_agente = get.getId_agente();
        this.nombre = get.getNombre();
        this.descripcion = get.getDescripcion();
        this.energia_max = get.getEnergia_max();
        this.habilidades = new ArrayList<>();
        this.sensores = new ArrayList<>();
        List<HabilidadModelo> hm = get.getHabilidades();
        List<SensorModelo> sm = get.getSensores();
        for(int i=0;i< hm.size();i++){
            habilidades.add(new HabilidadResponse(hm.get(i)));
        }
        for(int i=0;i< sm.size();i++){
            sensores.add(new SensorResponse(sm.get(i)));
        }
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

    public List<SensorResponse> getSensores() {
        return sensores;
    }

    public void setSensores(List<SensorResponse> sensores) {
        this.sensores = sensores;
    }

   
    
    
}
