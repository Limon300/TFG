/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class UsuarioSesionData {
    private Long id_usuario;
    List<Map<String,Long>> id_agentes_sesion;

    public UsuarioSesionData() {
    }
    

    public UsuarioSesionData(Long id_usuario, List<Map<String, Long>> id_agentes_sesion) {
        this.id_usuario = id_usuario;
        this.id_agentes_sesion = id_agentes_sesion;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<Map<String, Long>> getId_agentes_sesion() {
        return id_agentes_sesion;
    }

    public void setId_agentes_sesion(List<Map<String, Long>> id_agentes_sesion) {
        this.id_agentes_sesion = id_agentes_sesion;
    }

   


}
