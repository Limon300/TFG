/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.ObjetivoModelo;

/**
 *
 * @author usuario
 */
public class ObjetivoResponse {
    private Long id_obj;
    private String nombre;
    private Long valor;
    private String descripcion;
    private String categoria;

    public ObjetivoResponse() {
    }

    public ObjetivoResponse(ObjetivoModelo modelo) {
        this.id_obj = modelo.getId_obj();
        this.nombre = modelo.getNombre();
        this.valor = modelo.getValor();
        this.descripcion = modelo.getDescripcion();
        this.categoria = modelo.getCategoria();
    }

    public ObjetivoResponse(Long id_obj, String nombre, Long valor, String descripcion, String categoria) {
        this.id_obj = id_obj;
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

    
    public Long getId_obj() {
        return id_obj;
    }

    public void setId_obj(Long id_obj) {
        this.id_obj = id_obj;
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
