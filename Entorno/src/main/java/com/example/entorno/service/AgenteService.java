/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;



import com.example.entorno.dto.AgenteStoreRequest;
import com.example.entorno.dto.AgenteStoreResponse;
import com.example.entorno.dto.MapaResponse;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.repositories.AgenteRepository;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AgenteService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    SensorService servicioSensor;
    
    @Autowired
    AgenteRepository Local;
    
    public Optional<Long> addToStore(AgenteStoreRequest peticion){
        String usrUrl = "http://localhost:8000/api/agentes";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<AgenteStoreResponse> getDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/agentes/" + id;
        ResponseEntity<AgenteStoreResponse> agentResponse = restTemplate.getForEntity(usrUrl, AgenteStoreResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,AgenteStoreRequest peticion){
        String usrUrl = "http://localhost:8000/api/agentes/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/agentes/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<AgenteStoreResponse>> getAllDataFromStore(){
        String usrUrl = "http://localhost:8000/api/agentes";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }

    public ResponseEntity addSensor(Long Id, AgenteStoreRequest peticion) {
        String usrUrl = "http://localhost:8000/api/agentes/" + Id + "/sensores";
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity addHabilidad(Long Id, AgenteStoreRequest peticion) {
        String usrUrl = "http://localhost:8000/api/agentes/" + Id + "/habilidades";
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity removeSensor(Long Id, AgenteStoreRequest peticion) {
        String usrUrl = "http://localhost:8000/api/agentes/" + Id + "/sensores";
        //restTemplate.delete(usrUrl, peticion);
        HttpEntity<AgenteStoreRequest> entity = new HttpEntity<AgenteStoreRequest>(peticion);
        restTemplate.exchange(usrUrl, HttpMethod.DELETE, entity, ResponseEntity.class);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity removeHabilidad(Long Id, AgenteStoreRequest peticion) {
        String usrUrl = "http://localhost:8000/api/agentes/" + Id + "/habilidades";
        //restTemplate.delete(usrUrl, peticion);
        HttpEntity<AgenteStoreRequest> entity = new HttpEntity<AgenteStoreRequest>(peticion);
        restTemplate.exchange(usrUrl, HttpMethod.DELETE, entity, ResponseEntity.class);
        return ResponseEntity.ok().build();
    }
    
    //expandir
    Map<Long,Stack<Long>> insertarAgentes(List<AgenteStoreResponse> agentes,Long IdSesion,Long IdUsuario, MapaResponse mapa){
        Map<Long,Stack<Long>> salida = new HashMap();
        Random generador = new Random();
        long nuevo_id = 0;
        for(int i=0;i<agentes.size();i++){
            while(this.isLocal(nuevo_id)){
                nuevo_id = generador.nextLong(Long.MAX_VALUE);
            }
            Local.insertar(
                    nuevo_id, 
                    IdSesion, 
                    IdUsuario,
                    Long.valueOf(agentes.get(i).getId_agente()), 
                    mapa.getX_inicio(),
                    mapa.getY_inicio(),
                    Long.min(mapa.getEnergia_in(), agentes.get(i).getEnergia_max())
            );
            servicioSensor.insertarSensores(nuevo_id,agentes.get(i).getSensores());
            if(salida.containsKey(Long.valueOf(agentes.get(i).getId_agente()))){
                salida.get(Long.valueOf(agentes.get(i).getId_agente())).addElement(nuevo_id);
            }
            else{
                salida.put(Long.valueOf(agentes.get(i).getId_agente()),new Stack());
                salida.get(Long.valueOf(agentes.get(i).getId_agente())).addElement(nuevo_id);
            }
            
        }
        return salida;
    }

    private boolean isLocal(long nuevo_id) {
        return this.Local.findById(nuevo_id).isPresent();
    }

    void deleteAgentes(Long sessionId) {
        this.servicioSensor.deleteSensores(sessionId);
        Local.deleteObjetivosBySesion(sessionId);
        Local.deleteAgenteBySesion(sessionId);
    }
/**
 * Consulta de los agentes que pertenecen a un usuario en una sesion
 * @param sessionId
 * @param userId
 * @return mapa con los ids en el entorno y el store de los agentes de un usuario en una sesion
 */
    List<Map<String, Long>> getAgentes(Long sessionId, Long userId) {
        return Local.obtenerAgente(sessionId, userId);
    }
    
    Optional<AgenteModelo> getAgente(Long id){
        return this.Local.findById(id);
    }
    
    List<AgenteModelo> getAgentesFull(Long sessionId, Long userId){
        return Local.obtenerAgenteFull(sessionId, userId);
    }

    void moverAgente(Long id_agente, Long x, long y, long coste) {
        Local.updatePos(id_agente,x,y,coste);
    }
    
    public void ObjetivoAlcanzado(Long id_agente,Long id_obj){
        Local.insertAlcanzado(id_obj, id_agente);
    }

    void setEnergia(Long agente_id, Long energia) {
        Local.updateEnergia(agente_id,energia);
    }

    Optional<Long> getEnergia(Long agente_id) {
        List<Long> energia = Local.getEnergia(agente_id);
        if(energia.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(energia.get(0));
        }
    }
    
    void setProximaAccion(Long agente_id, Timestamp ultimo) {
        Local.setUltimo(agente_id,ultimo);
    }

    Optional<Timestamp> getProximaAccion(Long agente_id) {
        List<Timestamp> ultimo = Local.getUltimo(agente_id);
        if(ultimo.isEmpty() || ultimo.get(0) == null){
            return Optional.empty();
        }
        else{
            return Optional.of(ultimo.get(0));
        }
    }
    
    List<Long> getAlcanzados(Long objetivo_id) {
        return Local.findAlcanzadosByObjetivoId(objetivo_id);
    }

    boolean deleteAgentesByUser(Long user_id) {
        try {
            this.servicioSensor.deleteSensoresByUser(user_id);
            Local.deleteObjetivosByUsuario(user_id);
            Local.deleteAgenteByUsuario(user_id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    
    
    
}
