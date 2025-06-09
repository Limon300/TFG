/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.util.Map;
import java.util.Stack;

/**
 *
 * @author usuario
 */
public class SesionUserStart {
    Map<Long, Stack<Long>> id_agentes_sesion;

    public SesionUserStart() {
    }

    public SesionUserStart(Map<Long, Stack<Long>> id_agentes_sesion) {
        this.id_agentes_sesion = id_agentes_sesion;
    }

    public Map<Long, Stack<Long>> getId_agentes_sesion() {
        return id_agentes_sesion;
    }

    public void setId_agentes_sesion(Map<Long, Stack<Long>> id_agentes_sesion) {
        this.id_agentes_sesion = id_agentes_sesion;
    }

    
    
}
