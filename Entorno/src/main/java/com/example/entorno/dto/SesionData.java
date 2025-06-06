/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import com.example.entorno.models.SesionModelo;
import jakarta.persistence.Column;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class SesionData {
    
    private Long id_sesion;
    Long id_mapa;
    private Timestamp t_inicio;
    private Timestamp t_ultimo;
    private Long t_espera;
    private Long t_ciclo;
    List<UsuarioSesionData> id_agentes_sesion;

    public SesionData() {
    }
    
    

    public SesionData(SesionModelo sesion,List<UsuarioSesionData> agentes) {
        this.id_sesion = sesion.getId_sesion();
        this.id_mapa = sesion.getId_mapa();
        this.t_inicio = sesion.getT_inicio();
        this.t_ultimo = sesion.getT_ultimo();
        this.t_espera = sesion.getT_espera();
        this.t_ciclo = sesion.getT_ciclo();
        this.id_agentes_sesion = agentes;
    }

    public SesionData(Long id_sesion,Long id_mapa, Timestamp t_inicio, Timestamp t_ultimo, Long t_espera, Long t_ciclo, List<UsuarioSesionData> id_agentes_sesion) {
        this.id_sesion = id_sesion;
        this.id_mapa = id_mapa;
        this.t_inicio = t_inicio;
        this.t_ultimo = t_ultimo;
        this.t_espera = t_espera;
        this.t_ciclo = t_ciclo;
        this.id_agentes_sesion = id_agentes_sesion;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }
    
    

    public Long getId_mapa() {
        return id_mapa;
    }

    public void setId_mapa(Long id_mapa) {
        this.id_mapa = id_mapa;
    }

    public Timestamp getT_inicio() {
        return t_inicio;
    }

    public void setT_inicio(Timestamp t_inicio) {
        this.t_inicio = t_inicio;
    }

    public Timestamp getT_ultimo() {
        return t_ultimo;
    }

    public void setT_ultimo(Timestamp t_ultimo) {
        this.t_ultimo = t_ultimo;
    }

    public Long getT_espera() {
        return t_espera;
    }

    public void setT_espera(Long t_espera) {
        this.t_espera = t_espera;
    }

    public Long getT_ciclo() {
        return t_ciclo;
    }

    public void setT_ciclo(Long t_ciclo) {
        this.t_ciclo = t_ciclo;
    }

    public List<UsuarioSesionData> getId_agentes_sesion() {
        return id_agentes_sesion;
    }

    public void setId_agentes_sesion(List<UsuarioSesionData> id_agentes_sesion) {
        this.id_agentes_sesion = id_agentes_sesion;
    }



    
}
