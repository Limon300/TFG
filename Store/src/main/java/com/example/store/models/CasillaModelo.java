/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "casilla")
public class CasillaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mapa")
    private Long id_mapa;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "x")
    private Long x;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "y")
    private Long y;
    @Column(name = "z")
    private Long z;
    @Column(name = "coste")
    private Long coste;
    @Column(name = "apariencia")
    private String apariencia;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cas_poi", 
      joinColumns = {
        @JoinColumn(name = "id_mapa", referencedColumnName = "id_mapa"),
        @JoinColumn(name = "x", referencedColumnName = "x"),
        @JoinColumn(name = "y", referencedColumnName = "y")
      }, 
      inverseJoinColumns = @JoinColumn(name = "id_poi", referencedColumnName = "id_poi"))
    private List<POIModelo> POIs;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cas_obj", 
      joinColumns = {
        @JoinColumn(name = "id_mapa", referencedColumnName = "id_mapa"),
        @JoinColumn(name = "x", referencedColumnName = "x"),
        @JoinColumn(name = "y", referencedColumnName = "y")
      }, 
      inverseJoinColumns = @JoinColumn(name = "id_obj", referencedColumnName = "id_obj"))
    private List<ObjetivoModelo> Objetivos;
    
    
    public CasillaModelo() {}

    public CasillaModelo(Long id_mapa, Long x, Long y, Long z, Long coste, String apariencia, List<POIModelo> POIs, List<ObjetivoModelo> Objetivos) {
        this.id_mapa = id_mapa;
        this.x = x;
        this.y = y;
        this.z = z;
        this.coste = coste;
        this.apariencia = apariencia;
        this.POIs = POIs;
        this.Objetivos = Objetivos;
    }

    public List<ObjetivoModelo> getObjetivos() {
        return Objetivos;
    }

    public void setObjetivos(List<ObjetivoModelo> Objetivos) {
        this.Objetivos = Objetivos;
    }

    public List<POIModelo> getPOIs() {
        return POIs;
    }

    public void setPOIs(List<POIModelo> POIs) {
        this.POIs = POIs;
    }

    public Long getId_mapa() {
        return id_mapa;
    }

    public void setId_mapa(Long id_mapa) {
        this.id_mapa = id_mapa;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    public Long getCoste() {
        return coste;
    }

    public void setCoste(Long coste) {
        this.coste = coste;
    }

    public String getApariencia() {
        return apariencia;
    }

    public void setApariencia(String apariencia) {
        this.apariencia = apariencia;
    }
}