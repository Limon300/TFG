/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.store.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
CREATE TABLE sensor (
  ID_SENSOR INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  RADIO INTEGER DEFAULT 1
);
*/

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "sensor")
public class SensorModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id_sensor;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "radio")
    private Long radio;

    public SensorModelo() {
    }

    public SensorModelo(Long id_sensor, String nombre, String descripcion, String categoria, Long radio) {
        this.id_sensor = id_sensor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.radio = radio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Long getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(Long id_sensor) {
        this.id_sensor = id_sensor;
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

    public Long getRadio() {
        return radio;
    }

    public void setRadio(Long radio) {
        this.radio = radio;
    }

    
    
}
