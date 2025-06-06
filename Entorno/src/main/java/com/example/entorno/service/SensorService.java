/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;


import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.MapaResponse;
import com.example.entorno.dto.SensorResponse;
import com.example.entorno.dto.SensorStoreRequest;
import com.example.entorno.dto.SensorStoreResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.repositories.SensorRepository;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
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
public class SensorService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    SensorRepository Local;
    
    @Autowired
    MapaService ServicioMapa;
    
    public Optional<Long> addToStore(SensorStoreRequest peticion){
        String usrUrl = "http://localhost:8000/api/sensores";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<SensorStoreResponse> getDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/sensores/" + id;
        ResponseEntity<SensorStoreResponse> agentResponse = restTemplate.getForEntity(usrUrl, SensorStoreResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,SensorStoreRequest peticion){
        String usrUrl = "http://localhost:8000/api/sensores/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/sensores/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<SensorStoreResponse>> getAllDataFromStore(){
        String usrUrl = "http://localhost:8000/api/sensores";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    void insertarSensores(long agente_id, List<SensorStoreResponse> sensores) {
        Random generador = new Random();
        long nuevo_id = 0;
        for(int i=0;i < sensores.size();i++){
            while(Local.findById(nuevo_id).isPresent()){
                nuevo_id = generador.nextLong(Long.MAX_VALUE);
            }
            Local.insertar(agente_id, nuevo_id, sensores.get(i).getId_sensor(), Long.valueOf(0));
        }
    }

    void deleteSensores(Long sessionId) {
        Local.deleteSensorBySesion(sessionId);
    }

    void deleteSensoresByUser(Long user_id) {
        Local.deleteSensorByUsuario(user_id);
    }

    Optional<SensorResponse> usarSensor(Long sessionId, SesionData sesionData, AgenteModelo agenteLocal, SensorStoreResponse sensorStore) {
        switch(sensorStore.getCategoria()){
            case "VISION_FULL" -> {
                return this.visionFull(sesionData.getId_mapa(),agenteLocal,sensorStore.getRadio());
            }
            case "BATERIA" -> {
                return this.bateria(agenteLocal);
            }
            case "POSICION" -> {
                return this.posicion(sesionData.getId_mapa(),agenteLocal);
            }
            case "BUMPER_RIGHT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,1,0);
            }
            case "BUMPER_LEFT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,-1,0);
            }
            case "BUMPER_UP" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,0,1);
            }
            case "BUMPER_DOWN" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,0,-1);
            }
            case "BUMPER_UP_RIGHT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,1,1);
            }
            case "BUMPER_UP_LEFT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,-1,1);
            }
            case "BUMPER_DOWN_RIGHT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,1,-1);
            }
            case "BUMPER_DOWN_LEFT" -> {
                return this.bumper(sesionData.getId_mapa(),agenteLocal,-1,-1);
            }
            default -> {
                return Optional.empty();
            }
        }
    }
    
    private Optional<SensorResponse> posicion(Long id_mapa, AgenteModelo agenteLocal) {
        Optional<Casilla> casilla = this.ServicioMapa.getCasillaFromStore(
                id_mapa, agenteLocal.getX(), agenteLocal.getY());
        SensorResponse salida = new SensorResponse();
        if(casilla.isEmpty()){
            return Optional.of(salida);
        }
        else{
            salida.getCasillas().add(casilla.get());
            return Optional.of(salida);
        }
    }
    
    private Optional<SensorResponse> bumper(Long id_mapa, AgenteModelo agenteLocal, Integer X,Integer Y) {
        MapaResponse mapa = this.ServicioMapa.getDataFromStore(id_mapa).get();
        Long pos_x = agenteLocal.getX()+X;
        Long pos_y = agenteLocal.getY()+Y;
        Optional<Casilla> casilla;
        SensorResponse salida = new SensorResponse();
        if(mapa.getX_tam() > pos_x && pos_x >= 0 && mapa.getY_tam() > pos_y && pos_y >= 0){
            casilla = this.ServicioMapa.getCasillaFromStore(
                id_mapa, pos_x, pos_y);
            salida.getCasillas().add(casilla.get());
            return Optional.of(salida);
        }
        else{
            return Optional.of(salida);
        }
        
    }

    private Optional<SensorResponse> visionFull(Long id_mapa, AgenteModelo agenteLocal, Long radio) {
        List<Casilla> casillas = this.ServicioMapa.getCasillasFromStore(id_mapa).orElseThrow();
        double r = radio.doubleValue();
        SensorResponse salida = new SensorResponse();
        for(int i = 0;i<casillas.size();i++){
            double d = Math.sqrt(Math.pow(casillas.get(i).getX() - agenteLocal.getX(), 2) + Math.pow(casillas.get(i).getY() - agenteLocal.getY(), 2));
            if(r >= d){
                salida.getCasillas().add(casillas.get(i));
            }
        }
        return Optional.of(salida);
    }

    private Optional<SensorResponse> bateria(AgenteModelo agenteLocal) {
        SensorResponse salida = new SensorResponse();
        salida.setValor(agenteLocal.getEnergia());
        return Optional.of(salida);
    }
    
}
