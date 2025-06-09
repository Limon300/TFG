/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

/**
 *
 * @author usuario
 */
public class ActionRequest {
    private Long id_agente;
    private Long id_habilidad;

    public ActionRequest() {
    }

    public ActionRequest(Long id_agente, Long id_habilidad) {
        this.id_agente = id_agente;
        this.id_habilidad = id_habilidad;
    }
    
    

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }
    
}
