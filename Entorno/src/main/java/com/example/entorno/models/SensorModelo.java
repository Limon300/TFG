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
create table sensor (
  id_agente BIGINT,
  id_sensor BIGINT,
  id_sensor_store BIGINT not null,
  valor BIGINT,
  constraint claves_s primary key (id_sensor),
  constraint clave_sa foreign key (id_agente) references agentes(id_agente)
);
*/

@Entity
@Table(name = "sensor")
public class SensorModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id_sensor;
    
    @Column(name = "id_agente")
    private Long id_agente;
    
    @Column(name = "id_sensor_store")
    private Long id_sensor_store;
    
    @Column(name = "valor")
    private Long valor;

    public SensorModelo() {
    }

    public Long getId_sensor() {
        return id_sensor;
    }

    public void setId_sensor(Long id_sensor) {
        this.id_sensor = id_sensor;
    }

    public Long getId_agente() {
        return id_agente;
    }

    public void setId_agente(Long id_agente) {
        this.id_agente = id_agente;
    }

    public Long getId_sensor_store() {
        return id_sensor_store;
    }

    public void setId_sensor_store(Long id_sensor_store) {
        this.id_sensor_store = id_sensor_store;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

 
}
