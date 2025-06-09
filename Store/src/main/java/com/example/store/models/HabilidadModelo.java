/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.store.models;

import com.example.store.dto.HabilidadRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

/*
CREATE TABLE habilidad (
  ID_HABILIDAD INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  V_UP INTEGER DEFAULT 1,
  V_DOWN INTEGER DEFAULT 1,
  DESCRIPCION TEXT
);

*/

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "habilidad")
public class HabilidadModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidad")
    private Long id_habilidad;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "v_up")
    private Long v_up;
    @Column(name = "v_down")
    private Long v_down;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "categoria")
    private String categoria;

    

    public HabilidadModelo() {

    }
    
    public HabilidadModelo(Long id_habilidad, HabilidadRequest peticion) {
        this.id_habilidad = id_habilidad;
        this.nombre = peticion.getNombre();
        this.v_up = peticion.getV_up();
        this.v_down = peticion.getV_down();
        this.descripcion = peticion.getDescripcion();
        this.categoria = peticion.getCategoria();
    }

    public HabilidadModelo(Long id_habilidad, String nombre, Long v_up, Long v_down, String descripcion, String categoria) {
        this.id_habilidad = id_habilidad;
        this.nombre = nombre;
        this.v_up = v_up;
        this.v_down = v_down;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Long getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(Long id_habilidad) {
        this.id_habilidad = id_habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getV_up() {
        return v_up;
    }

    public void setV_up(Long v_up) {
        this.v_up = v_up;
    }

    public Long getV_down() {
        return v_down;
    }

    public void setV_down(Long v_down) {
        this.v_down = v_down;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
}
