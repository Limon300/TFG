/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;


/**
 *
 * @author usuario
 */
public class HabilidadResponse {
    private int id_habilidad;
    private String nombre;
    private Long v_up;
    private Long v_down;
    private String descripcion;
    private String categoria;

    public HabilidadResponse() {
    }
    
    public HabilidadResponse(int id_habilidad, String nombre, Long v_up, Long v_down, String descripcion, String categoria) {
        this.id_habilidad = id_habilidad;
        this.nombre = nombre;
        this.v_up = v_up;
        this.v_down = v_down;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public int getId_habilidad() {
        return id_habilidad;
    }

    public void setId_habilidad(int id_habilidad) {
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
