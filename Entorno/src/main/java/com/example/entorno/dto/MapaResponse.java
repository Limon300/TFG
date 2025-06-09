/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;




/**
 *
 * @author usuario
 */
public class MapaResponse {
    private Long id_mapa;
    private String nombre;
    private Long energia_in;
    private Long x_inicio;
    private Long y_inicio;
    private String descripcion;
    private Long x_tam;
    private Long y_tam;
    

    public MapaResponse() {}

    public MapaResponse(Long id_mapa, String nombre, Long energia_in, Long x_inicio, Long y_inicio, String descripcion, Long x_tam, Long y_tam) {
        this.id_mapa = id_mapa;
        this.nombre = nombre;
        this.energia_in = energia_in;
        this.x_inicio = x_inicio;
        this.y_inicio = y_inicio;
        this.descripcion = descripcion;
        this.x_tam = x_tam;
        this.y_tam = y_tam;
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
    
}
