/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.store.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario

CREATE TABLE agente (
  ID_AGENTE INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  ENERGIA_MAX INTEGER NOT NULL DEFAULT 100
);
 */

@Entity
@Table(name = "agente")
public class AgenteModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    private int id_agente;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "energia_max")
    private Long energia_max;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "agente_habilidad", 
      joinColumns = @JoinColumn(name = "id_agente", referencedColumnName = "id_agente"), 
      inverseJoinColumns = @JoinColumn(name = "id_habilidad", 
      referencedColumnName = "id_habilidad"))
    private List<HabilidadModelo> habilidades;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "agente_sensor", 
      joinColumns = @JoinColumn(name = "id_agente", referencedColumnName = "id_agente"), 
      inverseJoinColumns = @JoinColumn(name = "id_sensor", 
      referencedColumnName = "id_sensor"))
    private List<SensorModelo> sensores;

    public AgenteModelo() {
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

    public List<HabilidadModelo> getHabilidades() {
        return habilidades;
    }
    
    public List<Long> getIdHabilidades() {
        List<Long> salida = new ArrayList<>();
        for(int i=0;i<this.habilidades.size();i++){
            salida.add(this.habilidades.get(i).getId_habilidad());
        }
        return salida;
    }

    public void setHabilidades(List<HabilidadModelo> habilidades) {
        this.habilidades = habilidades;
    }

    public List<SensorModelo> getSensores() {
        return sensores;
    }
    
    public List<Long> getIdSensores() {
        List<Long> salida = new ArrayList<>();
        for(int i=0;i<this.sensores.size();i++){
            salida.add(this.sensores.get(i).getId_sensor());
        }
        return salida;
    }

    public void setSensores(List<SensorModelo> sensores) {
        this.sensores = sensores;
    }
    
    
    
}
