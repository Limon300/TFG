/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import java.sql.Timestamp;

/**
 *
 * @author usuario
 */
public class ActionResponse {

    private boolean exito;
    private Timestamp t_ultimo;
    
    public ActionResponse() {
        exito = true;
    }

    public ActionResponse(boolean exito,Timestamp ultimo) {
        this.exito = exito;
        this.t_ultimo = ultimo;
    }

    public Timestamp getT_ultimo() {
        return t_ultimo;
    }

    public void setT_ultimo(Timestamp t_ultimo) {
        this.t_ultimo = t_ultimo;
    }
    
    

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }
    
    
}
