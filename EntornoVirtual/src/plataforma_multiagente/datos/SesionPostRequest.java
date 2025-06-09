/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class SesionPostRequest {
    List<Long> agentes;
    Long mapa;
    Long t_ciclo;
    Long t_espera;

    public SesionPostRequest() {
    }

    public SesionPostRequest(List<Agente> agentes, Long mapa, int t_ciclo, int t_espera) {
        this.agentes = new ArrayList();
        agentes.forEach(a -> this.agentes.add(Long.valueOf(a.getId_store())));
        this.mapa = mapa;
        this.t_ciclo = Long.valueOf(t_ciclo);
        this.t_espera = Long.valueOf(t_espera);
    }

    public List<Long> getAgentes() {
        return agentes;
    }

    public void setAgentes(List<Long> agentes) {
        this.agentes = agentes;
    }

    public Long getMapa() {
        return mapa;
    }

    public void setMapa(Long mapa) {
        this.mapa = mapa;
    }

    public Long getT_ciclo() {
        return t_ciclo;
    }

    public void setT_ciclo(Long t_ciclo) {
        this.t_ciclo = t_ciclo;
    }

    public Long getT_espera() {
        return t_espera;
    }

    public void setT_espera(Long t_espera) {
        this.t_espera = t_espera;
    }
    
    
    
}
