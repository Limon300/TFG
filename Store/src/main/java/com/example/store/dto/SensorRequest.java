/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

/**
 *
 * @author usuario
 */
public class SensorRequest {
    private String nombre;
    private String descripcion;
    private Long radio;
    private String categoria;

    public SensorRequest() {
    }

    public SensorRequest(String nombre, String descripcion, Long radio, String categoria) {
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
