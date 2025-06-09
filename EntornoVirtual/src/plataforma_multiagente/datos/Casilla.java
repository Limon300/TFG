/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;


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
    @JsonProperty("y")
    private Long y;
    private Long z;
    private Long coste;
    private String apariencia;
    @JsonProperty("objetivos")
    private List<ObjetivoResponse> objetivos;
    @JsonProperty("puntos")
    private List<PoiResponse> puntos;

    public Casilla() {}

    public Casilla(Long x, Long y, Long z, Long coste, String apariencia, List<ObjetivoResponse> objetivos, List<PoiResponse> POIs) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coste = coste;
        this.apariencia = apariencia;
        this.objetivos = objetivos;
        this.puntos = POIs;
    }

    public List<ObjetivoResponse> getObjetivos() {
        if(objetivos == null){
            return new ArrayList<>();
        }
        else{
            return objetivos;
        }
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
