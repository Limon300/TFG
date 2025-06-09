/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

/**
 *
 * @author usuario
 */
public class ObjetivoRequest {
    private String nombre;
    private Long valor;
    private String descripcion;
    private String categoria;

    public ObjetivoRequest() {
    }

    public ObjetivoRequest(String nombre, Long valor, String descripcion, String categoria) {
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
