/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.SensorModelo;

/**
 *
 * @author usuario
 */
public class SensorResponse {
    private Long id_sensor;
    private String nombre;
    private String descripcion;
    private Long radio;
    private String categoria;

    public SensorResponse() {
    }

    public SensorResponse(SensorModelo copia) {
        this.id_sensor = copia.getId_sensor();
        this.nombre = copia.getNombre();
        this.descripcion = copia.getDescripcion();
        this.radio = copia.getRadio();
        this.categoria = copia.getCategoria();
    }

    public SensorResponse(Long id_sensor, String nombre, String descripcion, Long radio, String categoria) {
        this.id_sensor = id_sensor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.radio = radio;
        this.categoria = categoria;
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
