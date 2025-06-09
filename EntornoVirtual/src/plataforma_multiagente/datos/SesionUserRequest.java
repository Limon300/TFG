/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.util.List;

/**
 *
 * @author usuario
 */
public class SesionUserRequest {
    List<Long> agentes;
    Long id_usuario;

    public SesionUserRequest() {
    }

    public SesionUserRequest(List<Long> agentes, Long id_usuario) {
        this.agentes = agentes;
        this.id_usuario = id_usuario;
    }

    public List<Long> getAgentes() {
        return agentes;
    }

    public void setAgentes(List<Long> agentes) {
        this.agentes = agentes;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    
}
