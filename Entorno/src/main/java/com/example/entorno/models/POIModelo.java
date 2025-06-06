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
create table poi (
  id_poi BIGINT,
  id_sesion BIGINT,
  x BIGINT not null,
  y BIGINT not null,
  id_poi_store BIGINT not null,
  constraint claves_p primary key (id_poi),
  constraint clave_ps foreign key (id_sesion) references sesion(id_sesion)
);
*/

@Entity
@Table(name = "poi")
public class POIModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_poi")
    private Long id_poi;
    @Column(name = "id_sesion")
    private Long id_sesion;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name = "x")
    private Long x;
    @Column(name = "y")
    private Long y;
    @Column(name = "id_poi_store")
    private Long id_poi_store;

    public POIModelo() {
    }

    public POIModelo(Long id_poi, Long id_sesion, Long x, Long y, Long id_poi_store) {
        this.id_poi = id_poi;
        this.id_sesion = id_sesion;
        this.x = x;
        this.y = y;
        this.id_poi_store = id_poi_store;
    }

    public Long getId_poi() {
        return id_poi;
    }

    public void setId_poi(Long id_poi) {
        this.id_poi = id_poi;
    }

    public Long getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(Long id_sesion) {
        this.id_sesion = id_sesion;
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

    public Long getId_poi_store() {
        return id_poi_store;
    }

    public void setId_poi_store(Long id_poi_store) {
        this.id_poi_store = id_poi_store;
    }



    
}
