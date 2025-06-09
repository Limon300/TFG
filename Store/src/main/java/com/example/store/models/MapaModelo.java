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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/*
CREATE TABLE mapa (
  ID_MAPA INTEGER PRIMARY KEY,
  NOMBRE TEXT NOT NULL,
  ENERGIA_IN INTEGER NOT NULL DEFAULT 100,
  X_INICIO INTEGER NOT NULL,
  Y_INICIO INTEGER NOT NULL,
  DESCRIPCION TEXT,
  X_TAM INTEGER NOT NULL,
  Y_TAM INTEGER NOT NULL
);

*/

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "mapa")
public class MapaModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mapa")
    private Long id_mapa;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "energia_in")
    private Long energia_in;
    @Column(name = "x_inicio")
    private Long x_inicio;
    @Column(name = "y_inicio")
    private Long y_inicio;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "x_tam")
    private Long x_tam;
    @Column(name = "y_tam")
    private Long y_tam;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id_mapa")
    private List<CasillaModelo> casillas;

    public MapaModelo() {}

    public MapaModelo(Long id_mapa, String nombre, Long energia_in, Long x_inicio, Long y_inicio, String descripcion, Long x_tam, Long y_tam, List<CasillaModelo> casillas) {
        this.id_mapa = id_mapa;
        this.nombre = nombre;
        this.energia_in = energia_in;
        this.x_inicio = x_inicio;
        this.y_inicio = y_inicio;
        this.descripcion = descripcion;
        this.x_tam = x_tam;
        this.y_tam = y_tam;
        this.casillas = casillas;
    }

    public Long getId_mapa() {
        return id_mapa;
    }

    public void setId_mapa(Long id_mapa) {
        this.id_mapa = id_mapa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getEnergia_in() {
        return energia_in;
    }

    public void setEnergia_in(Long energia_in) {
        this.energia_in = energia_in;
    }

    public Long getX_inicio() {
        return x_inicio;
    }

    public void setX_inicio(Long x_inicio) {
        this.x_inicio = x_inicio;
    }

    public Long getY_inicio() {
        return y_inicio;
    }

    public void setY_inicio(Long y_inicio) {
        this.y_inicio = y_inicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getX_tam() {
        return x_tam;
    }

    public void setX_tam(Long x_tam) {
        this.x_tam = x_tam;
    }

    public Long getY_tam() {
        return y_tam;
    }

    public void setY_tam(Long y_tam) {
        this.y_tam = y_tam;
    }

    public List<CasillaModelo> getCasillas() {
        return casillas;
    }

    public void setCasillas(List<CasillaModelo> casillas) {
        this.casillas = casillas;
    }
    
    
    
}
