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
create table sesion (
  id_sesion BIGINT primary key,
  id_mapa BIGINT not null,
  t_inicio date not null,
  t_espera BIGINT not null,
  t_ciclo BIGINT not null,--t_ciclo en microsegundos
  t_ultimo date not null
);
*/

@Entity
@Table(name = "sesion")
public class SesionModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long id_sesion;
    @Column(name = "id_mapa")
    private Long id_mapa;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name = "t_inicio")
    private Timestamp t_inicio;
    @Column(name = "t_ultimo")
    private Timestamp t_ultimo;
    @Column(name = "t_espera")
    private Long t_espera;
    @Column(name = "t_ciclo")
    private Long t_ciclo;


    public SesionModelo() {

    }

    public SesionModelo(Long id_sesion, Long id_mapa, Timestamp t_inicio, Timestamp t_ultimo, Long t_espera, Long t_ciclo) {
        this.id_sesion = id_sesion;
        this.id_mapa = id_mapa;
        this.t_inicio = t_inicio;
        this.t_ultimo = t_ultimo;
        this.t_espera = t_espera;
        this.t_ciclo = t_ciclo;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }

    public Long getId_mapa() {
        return id_mapa;
    }

    public void setId_mapa(Long id_mapa) {
        this.id_mapa = id_mapa;
    }

    public Timestamp getT_inicio() {
        return t_inicio;
    }

    public void setT_inicio(Timestamp t_inicio) {
        this.t_inicio = t_inicio;
    }

    public Timestamp getT_ultimo() {
        return t_ultimo;
    }

    public void setT_ultimo(Timestamp t_ultimo) {
        this.t_ultimo = t_ultimo;
    }

    public Long getT_espera() {
        return t_espera;
    }

    public void setT_espera(Long t_espera) {
        this.t_espera = t_espera;
    }

    public Long getT_ciclo() {
        return t_ciclo;
    }

    public void setT_ciclo(Long t_ciclo) {
        this.t_ciclo = t_ciclo;
    }

    
}
