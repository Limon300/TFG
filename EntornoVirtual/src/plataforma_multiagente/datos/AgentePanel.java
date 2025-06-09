/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

/**
 *
 * @author usuario
 */
public class AgentePanel {
    Boolean propio;
    Long id;
    Long x;
    Long y;

    public AgentePanel() {
    }

    public AgentePanel(Boolean d,Long id, Long x, Long y) {
        this.propio = d;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Boolean getPropio() {
        return propio;
    }

    public void setPropio(Boolean propio) {
        this.propio = propio;
    }
    
    public void setXY(Long x,Long y){
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    
}
