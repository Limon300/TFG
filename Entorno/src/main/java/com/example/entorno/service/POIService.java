/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;


import com.example.entorno.dto.AgenteStoreResponse;
import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.HabilidadResponse;
import com.example.entorno.dto.POIRequest;
import com.example.entorno.dto.PoiResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.models.POIModelo;
import com.example.entorno.repositories.POIRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
public class POIService {
    @Value("${store}")
    private String store_adress;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    POIRepository Local;
    
    @Autowired
    AgenteService ServicioAgente;
    
    @Autowired
    LogService ServicioLog;
    
    public Optional<Long> addToStore(POIRequest peticion){
        String usrUrl = "http://"+store_adress+"/api/poi";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<PoiResponse> getDataFromStore(Long id){
        String usrUrl = "http://"+store_adress+"/api/poi/" + id;
        ResponseEntity<PoiResponse> agentResponse = restTemplate.getForEntity(usrUrl, PoiResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,POIRequest peticion){
        String usrUrl = "http://"+store_adress+"/api/poi/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://"+store_adress+"/api/poi/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<PoiResponse>> getAllDataFromStore(){
        String usrUrl = "http://"+store_adress+"/api/poi";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    void insertarPOI(List<Casilla> casillas, long sesion_id) {
        Random generador = new Random();
        Long nuevo_id = Long.valueOf(0);
        for(int i=0; i<casillas.size();i++){
            for(int j=0;j<casillas.get(i).getPuntos().size();j++){ 
                while(Local.findById(nuevo_id).isPresent()){
                    nuevo_id = generador.nextLong(Long.MAX_VALUE);
                }
                Local.insertar(
                        nuevo_id,
                        sesion_id,
                        casillas.get(i).getX(),
                        casillas.get(i).getY(),
                        casillas.get(i).getPuntos().get(j).getId_poi()
                );
            }
        }
    }

    void deletePOI(Long sessionId) {
        Local.deleteSesion(sessionId);
    }
    
    public void casillaPOIs(Long x,Long y,Long agente_id, AgenteStoreResponse agenteStore, SesionData sesion, Long sesion_id, HabilidadResponse habilidad_info) {
        List<Map<String,Long>> pois = Local.findByPosicion(sesion_id,x,y);
        for(int i=0;i<pois.size();i++){
            this.ejecutarPOI(pois.get(i).get("id_poi"), pois.get(i).get("id_poi_store"),sesion_id,habilidad_info.getCategoria(),agente_id,agenteStore);
        }
    }
    
    private void ejecutarPOI(Long Id_POI_local,Long Id_POI_store,Long sesion_id,String habilidad,Long agente_id,AgenteStoreResponse agenteStore){
        PoiResponse data = this.getDataFromStore(Id_POI_store).get();
        switch(data.getCategoria()){//añadir categoria
            case "BATERIA" -> {
                if(habilidad.contains("MOV")){
                    this.bateria(data,Id_POI_local,sesion_id,agente_id,agenteStore);
                }
                return;
            }
            default -> {
                return;
            }
        } 
    }

    private void bateria(PoiResponse data, Long Id_POI_local, Long sesion_id, Long agente_id,AgenteStoreResponse agenteStore) {
        System.out.println("Hola");
        long actual = this.ServicioAgente.getEnergia(agente_id).get();
        System.out.println(actual);
        Long energia = Long.min(actual + data.getValor(),agenteStore.getEnergia_max());
        this.ServicioAgente.setEnergia(agente_id,energia);
        this.ServicioLog.addLogPOI(sesion_id, Timestamp.valueOf(LocalDateTime.now()), agente_id, data.getNombre(), data.getCategoria());
        Local.deleteById(Id_POI_local);
    }
    
}
