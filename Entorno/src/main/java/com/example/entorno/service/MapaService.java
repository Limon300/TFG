/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;


import com.example.entorno.dto.MapaRequest;
import com.example.entorno.dto.MapaResponse;
import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.CasillaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
public class MapaService {
    @Value("${store}")
    private String store_adress;
    
    @Autowired
    RestTemplate restTemplate;
    
    
    public Optional<Long> addToStore(MapaRequest peticion){
        String usrUrl = "http://"+store_adress+"/api/mapas";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<MapaResponse> getDataFromStore(Long id){
        String usrUrl = "http://"+store_adress+"/api/mapas/" + id;
        ResponseEntity<MapaResponse> agentResponse = restTemplate.getForEntity(usrUrl, MapaResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,MapaRequest peticion){
        String usrUrl = "http://"+store_adress+"/api/mapas/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://"+store_adress+"/api/mapas/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<MapaResponse>> getAllDataFromStore(){
        String usrUrl = "http://"+store_adress+"/api/mapas";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    public Optional<Casilla> getCasillaFromStore(Long Id,Long X, Long Y) {
        String usrUrl = "http://"+store_adress+"/api/mapas/" + Id + "/casilla?X=" + X + "&Y=" + Y ; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        ResponseEntity<Casilla> agentResponse = restTemplate.getForEntity(usrUrl, Casilla.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<List<Casilla>> getCasillasFromStore(Long Id) {
        String usrUrl = "http://"+store_adress+"/api/mapas/" + Id + "/casillas";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        ObjectMapper mapper = new ObjectMapper();
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            List<Casilla> salida = new ArrayList();
            Casilla nuevo;
            for(int i = 0;i < agentResponse.getBody().size();i++){
                nuevo = mapper.convertValue(agentResponse.getBody().get(i), Casilla.class);
                salida.add(nuevo);
            }
            return Optional.of(salida);
        }
        return Optional.empty();
    }

    public ResponseEntity alterDataCasilla(Long Id, Long X, Long Y, CasillaRequest peticion) {
        String usrUrl = "http://"+store_adress+"/api/mapas/" + Id + "/casilla?X=" + X + "&Y=" + Y ;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();}

    public ResponseEntity deleteInCasillaFromStore(Long Id, Long X, Long Y, CasillaRequest peticion) {
        String usrUrl = "http://"+store_adress+"/api/mapas/" + Id + "/casilla?X=" + X + "&Y=" + Y ;
        HttpEntity<CasillaRequest> entity = new HttpEntity<CasillaRequest>(peticion);
        restTemplate.exchange(usrUrl, HttpMethod.DELETE, entity, ResponseEntity.class);
        return ResponseEntity.ok().build();
    }
    
}
