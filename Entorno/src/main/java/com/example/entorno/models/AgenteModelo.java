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
  id_agente BIGINT,
  id_sesion BIGINT,
  id_usuario BIGINT,
  id_agente_store BIGINT not null,
  x BIGINT not null,
  y BIGINT not null,
  t_ultimo timestamp(6),
  energia BIGINT not null,
  constraint claves_a primary key (id_agente),
  constraint clave_as foreign key (id_sesion) references sesion(id_sesion),
  constraint clave_au foreign key (id_usuario) references usuarios(id_usuario)
);
*/

@Entity
@Table(name = "agentes")
public class AgenteModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    private Long id_agente;
    @Column(name = "id_sesion")
    private Long id_sesion;
    @Column(name = "id_usuario")
    private Long id_usuario;
    @Column(name = "id_agente_store")
    private Long id_agente_store;
    @Column(name = "x")
    private Long x;
    @Column(name = "y")
    private Long y;
    @Column(name = "energia")
    private Long energia;
    @Column(name = "t_ultimo")
    private Timestamp t_ultimo;

    public AgenteModelo() {
    }

    public AgenteModelo(Long id_agente, Long id_sesion, Long id_usuario, Long id_agente_store, Long x, Long y, Long energia) {
        this.id_agente = id_agente;
        this.id_sesion = id_sesion;
        this.id_usuario = id_usuario;
        this.id_agente_store = id_agente_store;
        this.x = x;
        this.y = y;
        this.energia = energia;
        this.t_ultimo = null;
    }

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_agente_store() {
        return id_agente_store;
    }

    public void setId_agente_store(Long id_agente_store) {
        this.id_agente_store = id_agente_store;
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

    public Long getEnergia() {
        return energia;
    }

    public void setEnergia(Long energia) {
        this.energia = energia;
    }

    public Timestamp getT_ultimo() {
        return t_ultimo;
    }

    public void setT_ultimo(Timestamp t_ultimo) {
        this.t_ultimo = t_ultimo;
    }


    
    
}
