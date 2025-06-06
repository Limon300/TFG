/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;


import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.HabilidadResponse;
import com.example.entorno.dto.ObjetivoRequest;
import com.example.entorno.dto.ObjetivoResponse;
import com.example.entorno.dto.ObjetivoSesionResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.models.ObjetivoModelo;
import com.example.entorno.repositories.ObjetivoRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
public class ObjetivoService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    ObjetivoRepository Local;
    
    @Autowired
    LogService servicioLog;
    
    @Autowired
    AgenteService servicioAgente;
    
    public Optional<Long> addToStore(ObjetivoRequest peticion){
        String usrUrl = "http://localhost:8000/api/objetivos";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<ObjetivoResponse> getDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/objetivos/" + id;
        ResponseEntity<ObjetivoResponse> agentResponse = restTemplate.getForEntity(usrUrl, ObjetivoResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,ObjetivoRequest peticion){
        String usrUrl = "http://localhost:8000/api/objetivos/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/objetivos/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<ObjetivoResponse>> getAllDataFromStore(){
        String usrUrl = "http://localhost:8000/api/objetivos";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    void insertarObjetivos(List<Casilla> casillas, long sesion_id) {
        Random generador = new Random();
        Long nuevo_id = Long.valueOf(0);
        for(int i=0; i<casillas.size();i++){
            for(int j=0;j<casillas.get(i).getObjetivos().size();j++){ 
                while(Local.findById(nuevo_id).isPresent()){
                    nuevo_id = generador.nextLong(Long.MAX_VALUE);
                }
                Local.insertar(
                        sesion_id,
                        nuevo_id,
                        "on",
                        Long.valueOf(0),
                        casillas.get(i).getObjetivos().get(j).getId_obj(),
                        casillas.get(i).getX(),
                        casillas.get(i).getY()
                );
                if(j == 0){
                    Local.insertarActual(sesion_id,nuevo_id);
                }
            }
        }
    }

    void deleteObjetivos(Long sessionId) {
        Local.deleteActual(sessionId);
        Local.deleteSesion(sessionId);
    }
    /**
     * Funcion para ejecutar un objetivo, comprueba si la accion es adecuada para el objetivo pero no la posicion
     * @param user_id
     * @param objetivoEntorno
     * @param agente_id
     * @param sesion_id
     * @param sesion
     * @param habilidad 
     */
    public void EjecutarObjetivo(Long user_id,ObjetivoModelo objetivoEntorno, Long agente_id, Long sesion_id,SesionData sesion, HabilidadResponse habilidad) {
        ObjetivoResponse objStore = this.getDataFromStore(objetivoEntorno.getId_objetivo_store()).orElseThrow();
        switch(objStore.getCategoria()){
            case "POS" -> {
                if(habilidad.getCategoria().contains("MOV")){
                    this.obj_posicion(user_id,objetivoEntorno,objStore,agente_id,sesion_id,sesion);
                }
                return;
            }
            default -> {
                
                return;
            }
        }
    }

    void obj_posicion(Long user_id,ObjetivoModelo objetivoEntorno,ObjetivoResponse objStore, Long agente_id, Long sesion_id, SesionData sesion) {
        Local.updateObjetivoStatus(objetivoEntorno.getId_objetivo(),"off");
        Local.deleteActual(sesion_id);
        List<Long> nuevos = Local.findOn(sesion_id);
        this.servicioAgente.ObjetivoAlcanzado(agente_id, objetivoEntorno.getId_objetivo());
        if(!nuevos.isEmpty()){
            Local.insertarActual(sesion_id, nuevos.get(0));
        }
        this.servicioLog.addLogObjetivo(
            user_id,
            sesion_id,
            Timestamp.valueOf(LocalDateTime.now()),
            agente_id,
            objStore.getNombre(),
            objStore.getCategoria()
        );
    }

    Optional<ObjetivoModelo> getActual(Long sesion) {
        List<Long> actual = Local.getActual(sesion);
        if(actual.isEmpty()){
            return Optional.empty();
        }
        else{
            return Local.findById(actual.get(0));
        }
    }

    List<ObjetivoSesionResponse> consultarObjetivos(Long sessionId) {
        List<ObjetivoSesionResponse> salida = new ArrayList();
        List<Map<String,Object>> objetivos = this.Local.findBySesion(sessionId);
        for(int i=0;i < objetivos.size();i++){
            salida.add(new ObjetivoSesionResponse(objetivos.get(i),this.servicioAgente.getAlcanzados((Long) objetivos.get(i).get("id_objetivo"))));
        }
        return salida;
    }
    
}
