/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.POIModelo;

/**
 *
 * @author usuario
 */
public class PoiResponse {
    private Long id_poi;
    private String nombre;
    private Long valor;
    private String descripcion;
    private String categoria;

    public PoiResponse() {
    }

    public PoiResponse(POIModelo modelo) {
        this.id_poi = modelo.getId_poi();
        this.nombre = modelo.getNombre();
        this.valor = modelo.getValor();
        this.descripcion = modelo.getDescripcion();
        this.categoria = modelo.getCategoria();
    }

    public PoiResponse(Long id_poi, String nombre, Long valor, String descripcion, String categoria) {
        this.id_poi = id_poi;
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId_poi() {
        return id_poi;
    }

    public void setId_poi(Long id_poi) {
        this.id_poi = id_poi;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
