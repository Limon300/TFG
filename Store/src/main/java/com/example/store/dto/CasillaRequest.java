/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.dto;

import com.example.store.models.CasillaModelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usuario
 */
public class CasillaRequest {
    private Long x;
    private Long y;
    private Long z;
    private Long coste;
    private String apariencia;
    private List<Long> objetivos;
    private List<Long> POIs;

    public CasillaRequest() {}
    
    private Long IntegerLong(Integer input){
        return input.longValue();
    } 

    public CasillaRequest(Long z, Long coste, String apariencia) {
        this.z = z;
        this.coste = coste;
        this.apariencia = apariencia;
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
    
    

    public List<Long> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(List<Long> objetivos) {
        this.objetivos = objetivos;
    }

    public List<Long> getPOIs() {
        return POIs;
    }

    public void setPOIs(List<Long> POIs) {
        this.POIs = POIs;
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
