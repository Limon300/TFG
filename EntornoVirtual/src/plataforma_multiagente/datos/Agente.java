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
public class Agente {
    private int id_store;
    private String nombre_juego;
    private String nombre;
    private Long id_juego;
    private String descripcion;
    private Long energia_max;
    private List<HabilidadResponse> habilidades;
    private List<SensorStoreResponse> sensores;

    public Agente() {
    }
    
    
    public Agente(String nombre_juego, AgenteStoreResponse store) {
        this.id_store = store.getId_agente();
        this.nombre_juego = nombre_juego;
        this.nombre = store.getNombre();
        this.descripcion = store.getDescripcion();
        this.energia_max = store.getEnergia_max();
        this.habilidades = store.getHabilidades();
        this.sensores = store.getSensores();
    }
    
    public Agente(String nombre_juego, AgenteStoreResponse store,Long id_juego) {
        this.id_store = store.getId_agente();
        this.nombre_juego = nombre_juego;
        this.nombre = store.getNombre();
        this.descripcion = store.getDescripcion();
        this.energia_max = store.getEnergia_max();
        this.habilidades = store.getHabilidades();
        this.sensores = store.getSensores();
        this.id_juego = id_juego;
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

    public int getId_store() {
        return id_store;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public Long getId_juego() {
        return id_juego;
    }

    public void setId_juego(Long id_juego) {
        this.id_juego = id_juego;
    }

    public String getNombre_juego() {
        return nombre_juego;
    }

    public void setNombre_juego(String nombre_juego) {
        this.nombre_juego = nombre_juego;
    }
    
    

   @Override
    public String toString(){
        String info = "Nombre: " + this.getNombre()+ "\n";
        info += "Energia maxima: " + this.getEnergia_max()+ "\n";
        info += "Descripcion: " + this.getDescripcion() + "\n";
        info += "Habilidades: ";
        for(int i=0;i<this.getHabilidades().size();i++){
            info += this.getHabilidades().get(i).getNombre();
            if(i + 1 != this.getHabilidades().size()){
                info += ", ";
            }
        }
        info += "\n";
        info += "Sensores: ";
        for(int i=0;i<this.getSensores().size();i++){
            info += this.getSensores().get(i).getNombre();
            if(i + 1 != this.getSensores().size()){
                info += ", ";
            }
        }
        info += "\n";
        return info;
    }
    
    
}
