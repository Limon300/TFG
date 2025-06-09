/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.CasillaModelo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class Casilla {
    private Long x;
    private Long y;
    private Long z;
    private Long coste;
    private String apariencia;
    private List<ObjetivoResponse> objetivos;
    @JsonProperty("puntos")
    private List<PoiResponse> puntos;

    public Casilla() {}
    
    public Casilla(CasillaModelo modelo) {
        this.x = modelo.getX();
        this.y = modelo.getY();
        this.z = modelo.getZ();
        this.coste = modelo.getCoste();
        this.apariencia = modelo.getApariencia();
        this.objetivos = new ArrayList<>();
        for(int i=0;i<modelo.getObjetivos().size();i++){
            this.objetivos.add(new ObjetivoResponse(modelo.getObjetivos().get(i)));
        }
        this.puntos = new ArrayList<>();
        for(int i=0;i<modelo.getPOIs().size();i++){
            this.puntos.add(new PoiResponse(modelo.getPOIs().get(i)));
        }
    }
    

    public Casilla(Map<String, Object> consulta, List<Map<String, Object>> obj,List<Map<String, Object>> poi) {
        this.x = this.IntegerLong((Integer) consulta.get("X"));
        this.y = this.IntegerLong((Integer) consulta.get("Y"));
        this.z = this.IntegerLong((Integer) consulta.get("Z"));
        this.coste = this.IntegerLong((Integer) consulta.get("COSTE"));
        this.apariencia = (String) consulta.get("APARIENCIA");
        this.objetivos = new ArrayList<>();
        for(int i=0;i<obj.size();i++){
            this.objetivos.add(new ObjetivoResponse(
                    this.IntegerLong((Integer) obj.get(i).get("ID_OBJ")),
                    (String) obj.get(i).get("nombre"),
                    this.IntegerLong((Integer) obj.get(i).get("VALOR")),
                    (String) obj.get(i).get("DESCRIPCION"),
                    (String) obj.get(i).get("categoria")
            ));
        }
        this.puntos = new ArrayList<>();
        for(int i=0;i<poi.size();i++){
            this.puntos.add(new PoiResponse(
                    this.IntegerLong((Integer) poi.get(i).get("ID_POI")),
                    (String) poi.get(i).get("nombre"),
                    this.IntegerLong((Integer) poi.get(i).get("VALOR")),
                    (String) poi.get(i).get("DESCRIPCION"),
                    (String) poi.get(i).get("categoria")
            ));
        }
        
    }
    
    private Long IntegerLong(Integer input){
        return input.longValue();
    } 

    public Casilla(Long x, Long y, Long z, Long coste, String apariencia) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coste = coste;
        this.apariencia = apariencia;
    }

    public List<ObjetivoResponse> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<ObjetivoResponse> objetivos) {
        this.objetivos = objetivos;
    }

    public List<PoiResponse> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<PoiResponse> puntos) {
        this.puntos = puntos;
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

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        this.z = z;
    }

    public Long getCoste() {
        return coste;
    }

    public void setCoste(Long coste) {
        this.coste = coste;
    }

    public String getApariencia() {
        return apariencia;
    }

    public void setApariencia(String apariencia) {
        this.apariencia = apariencia;
    }
    
    
}
