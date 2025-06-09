/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

import com.example.entorno.models.LogModelo;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author usuario
 */

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
public class LogResponse {
    private Long id_sesion;
    private Timestamp fecha;
    private Long id_entrada;
    private String entrada;
    private String evento;
    private Long id_sujeto;
    private Long val_1;
    private Long val_2;

    public LogResponse() {
    }
    
    public LogResponse(Map<String,Object> datos){
        this.id_sesion = (Long) datos.get("id_sesion");
        this.fecha = (Timestamp) datos.get("fecha");
        this.id_entrada = (Long) datos.get("id_entrada");
        this.entrada = (String) datos.get("entrada");
        this.evento = (String) datos.get("evento");
        this.id_sujeto = (Long) datos.get("id_sujeto");
        this.val_1 = (Long) datos.get("val_1");
        this.val_2 = (Long) datos.get("val_2");
        
    }
    
    public LogResponse(LogModelo modelo) {
        this.id_sesion = modelo.getId_sesion();
        this.fecha = modelo.getFecha();
        this.id_entrada = modelo.getId_entrada();
        this.entrada = modelo.getEntrada();
        this.evento = modelo.getEvento();
        this.id_sujeto = modelo.getId_sujeto();
        this.val_1 = modelo.getVal_1();
        this.val_2 = modelo.getVal_2();
    }

    public LogResponse(Long id_sesion, Timestamp fecha, Long id_entrada, String entrada, String evento, Long id_sujeto, Long val_1, Long val_2) {
        this.id_sesion = id_sesion;
        this.fecha = fecha;
        this.id_entrada = id_entrada;
        this.entrada = entrada;
        this.evento = evento;
        this.id_sujeto = id_sujeto;
        this.val_1 = val_1;
        this.val_2 = val_2;
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
    
    

    
    public Long getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(Long id_entrada) {
        this.id_entrada = id_entrada;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
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

    
    
}
