/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

/**
 *
 * @author usuario
 */
public class SensorStoreRequest {
    private String nombre;
    private String descripcion;
    private String categoria;
    private Long radio;

    public SensorStoreRequest() {
    }

    public SensorStoreRequest(String nombre, String descripcion, String categoria, Long radio) {
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
