/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.util.List;
import java.util.Map;

/**
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

create table actual (
  id_sesion BIGINT,
  id_objetivo BIGINT,
  constraint claves_ac primary key (id_sesion),
  constraint clave_aco foreign key (id_objetivo) references objetivos(id_objetivo),
  constraint clave_acs foreign key (id_sesion) references sesion(id_sesion)
);

create table alcanzado (
  id_agente BIGINT,
  id_objetivo BIGINT,
  constraint claves_alc primary key (id_objetivo,id_agente),
  constraint clave_alco foreign key (id_objetivo) references objetivos(id_objetivo),
  constraint clave_alca foreign key (id_agente) references agentes(id_agente)
);
 */
public class ObjetivoSesionResponse {
    private Long id_obj;
    private Long id_obj_store;
    private String estatus;
    private Long compartido;
    private Long X;
    private Long Y;
    private List<Long> agentes;

    public ObjetivoSesionResponse() {
    }
    
    public ObjetivoSesionResponse(Map<String,Object> modelo, List<Long> agentes) {
        this.id_obj = (Long) modelo.get("id_objetivo");
        this.id_obj_store = (Long) modelo.get("id_objetivo_store");
        this.estatus = (String) modelo.get("estatus");
        this.compartido = (Long) modelo.get("compartido");
        this.X = (Long) modelo.get("X");
        this.Y = (Long) modelo.get("Y");
        this.agentes = agentes;
    }
    

    public ObjetivoSesionResponse(Long id_obj, Long id_obj_store, String estatus, Long compartido, Long X, Long Y, List<Long> agentes) {
        this.id_obj = id_obj;
        this.id_obj_store = id_obj_store;
        this.estatus = estatus;
        this.compartido = compartido;
        this.X = X;
        this.Y = Y;
        this.agentes = agentes;
    }
    


    public Long getId_obj() {
        return id_obj;
    }

    public void setId_obj(Long id_obj) {
        this.id_obj = id_obj;
    }

    public Long getId_obj_store() {
        return id_obj_store;
    }

    public void setId_obj_store(Long id_obj_store) {
        this.id_obj_store = id_obj_store;
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

    public Long getX() {
        return X;
    }

    public void setX(Long X) {
        this.X = X;
    }

    public Long getY() {
        return Y;
    }

    public void setY(Long Y) {
        this.Y = Y;
    }

    public List<Long> getAgentes() {
        return agentes;
    }

    public void setAgentes(List<Long> agentes) {
        this.agentes = agentes;
    }

    
    
}
