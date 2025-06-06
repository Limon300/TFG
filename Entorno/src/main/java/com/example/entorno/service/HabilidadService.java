/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;


import com.example.entorno.dto.ActionResponse;
import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.HabilidadRequest;
import com.example.entorno.dto.HabilidadResponse;
import com.example.entorno.dto.ObjetivoResponse;
import com.example.entorno.dto.PoiResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.models.ObjetivoModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author usuario
 */
@Service
public class HabilidadService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    MapaService servicioMapa;
    
    @Autowired
    AgenteService servicioAgente;
    
    @Autowired
    ObjetivoService servicioObjetivo;
    
    @Autowired
    POIService servicioPOI;
    
    @Autowired
    LogService servicioLog;
    
    public Optional<Long> addToStore(HabilidadRequest peticion){
        String usrUrl = "http://localhost:8000/api/habilidades";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<HabilidadResponse> getDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/habilidades/" + id;
        ResponseEntity<HabilidadResponse> agentResponse = restTemplate.getForEntity(usrUrl, HabilidadResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,HabilidadRequest peticion){
        String usrUrl = "http://localhost:8000/api/habilidades/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/habilidades/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<HabilidadResponse>> getAllDataFromStore(){
        String usrUrl = "http://localhost:8000/api/habilidades";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    public ActionResponse mov_basic(Long sesion_id,SesionData sesion, AgenteModelo agente, HabilidadResponse habilidad_info,Long mov_x,Long mov_y) {
        //Se comprueba que la habilidad puede ejecutarse correctamente
        Optional<Casilla> destino;
        try {
                destino = this.servicioMapa.getCasillaFromStore(sesion.getId_mapa(), agente.getX() + mov_x, agente.getY() + mov_y);
            } catch (Exception e) {
                return new ActionResponse(false,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
        }     
        Casilla origen = this.servicioMapa.getCasillaFromStore(sesion.getId_mapa(), agente.getX(), agente.getY()).orElseThrow();
        if(destino.isEmpty()){
            return new ActionResponse(false,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
        }
        if(destino.get().getCoste() > agente.getEnergia()){
            return new ActionResponse(false,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
        }
        Long diferencia = destino.get().getZ() - origen.getZ();
        if(diferencia < 0){
            if( habilidad_info.getV_down() < (-diferencia) ){
                return new ActionResponse(false,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
            }
        }
        else if(diferencia > 0){
            if( habilidad_info.getV_up()< (diferencia) ){
                return new ActionResponse(false,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
            }
        }
        //Una vez que estan hechas todas las comprobaciones se ejecutan los efectos
        this.servicioAgente.moverAgente(agente.getId_agente(),agente.getX() + mov_x,agente.getY() + mov_y,agente.getEnergia() - destino.get().getCoste());
        //Insertar log
        this.servicioLog.addLogHabilidad(
                sesion_id,Timestamp.valueOf(LocalDateTime.now()),
                agente.getId_agente(),
                habilidad_info.getNombre(),
                habilidad_info.getCategoria(),
                agente.getX() + mov_x,
                agente.getY() + mov_y);
        //Interaccion con poi
        this.servicioPOI.casillaPOIs(
                destino.get().getX(),
                destino.get().getY(),
                agente.getId_agente(),
                this.servicioAgente.getDataFromStore(agente.getId_agente_store()).get(),
                sesion,
                sesion_id,
                habilidad_info
        );
        //Interaccion con objetivos
        Optional<ObjetivoModelo> actual = this.servicioObjetivo.getActual(sesion_id);
        if(actual.isPresent()){
            if(Objects.equals(actual.get().getX(), destino.get().getX()) && Objects.equals(actual.get().getY(), destino.get().getY())){
                this.servicioObjetivo.EjecutarObjetivo(agente.getId_usuario(),actual.get(), agente.getId_agente(), sesion_id, sesion, habilidad_info);
            }
        }        
        return new ActionResponse(true,this.servicioAgente.getProximaAccion(agente.getId_agente()).get());
    }
    
}
