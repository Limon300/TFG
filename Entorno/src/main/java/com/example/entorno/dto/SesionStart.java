/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import java.util.Map;
import java.util.Stack;

/**
 *
 * @author usuario
 */
public class SesionStart {
    Long id_sesion;
    Map<Long,Stack<Long>> id_agentes_sesion;

    public SesionStart() {
    }

    public SesionStart(Long id_sesion, Map<Long,Stack<Long>> id_agentes) {
        this.id_sesion = id_sesion;
        this.id_agentes_sesion = id_agentes;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }

    public Map<Long, Stack<Long>> getId_agentes_sesion() {
        return id_agentes_sesion;
    }

    public void setId_agentes(Map<Long, Stack<Long>> id_agentes_sesion) {
        this.id_agentes_sesion = id_agentes_sesion;
    }

    
    
    
}
