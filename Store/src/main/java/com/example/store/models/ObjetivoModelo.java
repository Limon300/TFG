/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.store.models;

import com.example.store.dto.ObjetivoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
CREATE TABLE objetivos (
  ID_OBJ INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  VALOR INTEGER
);

*/

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "objetivos")
public class ObjetivoModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obj")
    private Long id_obj;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private Long valor;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "categoria")
    private String categoria;

    public ObjetivoModelo() {
    }

    public ObjetivoModelo(Long id_obj, String nombre, Long valor, String descripcion, String categoria) {
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
