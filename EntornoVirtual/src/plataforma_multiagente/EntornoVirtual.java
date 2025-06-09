/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente;

import java.util.ArrayList;
import java.util.List;
import plataforma_multiagente.datos.ActionResponse;
import plataforma_multiagente.datos.Agente;
import plataforma_multiagente.datos.HabilidadResponse;
import plataforma_multiagente.datos.ObjetivoResponse;
import plataforma_multiagente.datos.ObjetivoSesionResponse;
import plataforma_multiagente.datos.SensorResponse;
import plataforma_multiagente.datos.SesionData;

/**
 *
 * @author usuario
 */
    

public class EntornoVirtual {
    private EntornoLocal Entorno;
    /**
     * 
     */
    public EntornoVirtual() {
        Entorno = new EntornoLocal();
    }

    public EntornoVirtual(String IP) {
        Entorno = new EntornoLocal(IP);
    }
    
    public EntornoVirtual(String IP,Long id,String password) {
        Entorno = new EntornoLocal(IP,id,password);
    }
    
    public List<SesionData> ConsultarSesiones(){
        Entorno.PedirVerSesion();
        
        return Entorno.getSesiones_actuales();
    }
    
    public SensorResponse UsarSensor(Long id_agente,Long id_sensor){
        return Entorno.PeticionSensor(id_agente, id_sensor);
    }
    
    public ActionResponse UsarHabilidad(Long id_agente,Long id_habilidad){
        return Entorno.PeticionHabilidad(id_agente, id_habilidad);
    }
    
    public List<ObjetivoSesionResponse> ConsultarObjetivos(){
        return Entorno.PeticionObjetivos();
    }
    /**
     * 
     * @return 
     */
    public List<Agente> ConsultarMisAgentes(){
        return Entorno.getAgentesActuales();
    }
    
}
