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
import java.sql.Time;
import java.sql.Timestamp;

/*
create table logs (
  id_entrada BIGINT,
  id_sesion BIGINT,
  fecha timestamp not null,
  entrada VARCHAR(255),
  constraint claves_l primary key (id_entrada),
  constraint clave_ls foreign key (id_sesion) references sesion(id_sesion)
);
*/

@Entity
@Table(name = "logs")
public class LogModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Long id_entrada;
    
    @Column(name = "id_sesion")
    private Long id_sesion;
    
    @Column(name = "fecha")
    private Timestamp fecha;
    
    @Column(name = "entrada")
    private String entrada;
    
    @Column(name = "evento")
    private String evento;
    
    @Column(name = "id_sujeto")
    private Long id_sujeto;
    
    @Column(name = "val_1")
    private Long val_1;
    
    @Column(name = "val_2")
    private Long val_2;

    public LogModelo() {
    }

    public Long getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(Long id_entrada) {
        this.id_entrada = id_entrada;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }



    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Long getId_sujeto() {
        return id_sujeto;
    }

    public void setId_sujeto(Long id_sujeto) {
        this.id_sujeto = id_sujeto;
    }

    public Long getVal_1() {
        return val_1;
    }

    public void setVal_1(Long val_1) {
        this.val_1 = val_1;
    }

    public Long getVal_2() {
        return val_2;
    }

    public void setVal_2(Long val_2) {
        this.val_2 = val_2;
    }
    
    


}
