/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.entorno.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

/*
create table objetivos (
  id_sesion BIGINT,
  id_objetivo BIGINT,
  estatus VARCHAR(255),
  compartido BIGINT,
  id_objetivo_store BIGINT,
  x BIGINT not null,
  y BIGINT not null,
  constraint claves_o primary key (id_objetivo),
  constraint clave_os foreign key (id_sesion) references sesion(id_sesion)
);
*/

@Entity
@Table(name = "objetivos")
public class ObjetivoModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Long id_objetivo;
    
    @Column(name = "id_sesion")
    private Long id_sesion;
    
    @Column(name = "estatus")
    private String estatus;
    
    @Column(name = "compartido")
    private Long compartido;
    
    @Column(name = "id_objetivo_store")
    private Long id_objetivo_store;
    
    @Column(name = "x")
    private Long x;
    
    @Column(name = "y")
    private Long y;

    public ObjetivoModelo() {
    }

    public Long getId_objetivo() {
        return id_objetivo;
    }

    public void setId_objetivo(Long id_objetivo) {
        this.id_objetivo = id_objetivo;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Long getCompartido() {
        return compartido;
    }

    public void setCompartido(Long compartido) {
        this.compartido = compartido;
    }

    public Long getId_objetivo_store() {
        return id_objetivo_store;
    }

    public void setId_objetivo_store(Long id_objetivo_store) {
        this.id_objetivo_store = id_objetivo_store;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    

    
}
