/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;

import com.example.entorno.dto.UsuarioDataResponse;
import com.example.entorno.dto.UsuarioPostRequest;
import com.example.entorno.dto.UsuarioRequest;
import com.example.entorno.dto.UsuarioResponse;
import com.example.entorno.models.UsuariosModelo;
import com.example.entorno.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
public class UsuarioService {
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    AgenteService servicioAgente;
    
    @Autowired
    UsuarioRepository Local;
    
    public Optional<Long> addToStore(UsuarioPostRequest peticion){
        String usrUrl = "http://localhost:8000/api/usuarios";
        ResponseEntity<Long> agentResponse = restTemplate.postForEntity(usrUrl, peticion, Long.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<UsuarioResponse> getFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/usuarios/" + id + "/login";
        ResponseEntity<UsuarioResponse> agentResponse = restTemplate.getForEntity(usrUrl, UsuarioResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public Optional<UsuarioDataResponse> getDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/usuarios/" + id + "/data";
        ResponseEntity<UsuarioDataResponse> agentResponse = restTemplate.getForEntity(usrUrl, UsuarioDataResponse.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    
    public ResponseEntity alterDataFromStore(Long id,UsuarioPostRequest peticion){
        String usrUrl = "http://localhost:8000/api/usuarios/" + id;
        restTemplate.put(usrUrl, peticion);
        return ResponseEntity.ok().build();
    }
    
    public ResponseEntity deleteDataFromStore(Long id){
        String usrUrl = "http://localhost:8000/api/usuarios/" + id;
        restTemplate.delete(usrUrl);
        return ResponseEntity.ok().build();
    }
    
    public Optional<List<UsuarioDataResponse>> getAllDataFromStore(){
        String usrUrl = "http://localhost:8000/api/usuarios";
        ResponseEntity<List> agentResponse = restTemplate.getForEntity(usrUrl, List.class);
        if(agentResponse.getStatusCode().is2xxSuccessful()){
            return Optional.of(agentResponse.getBody());
        }
        return Optional.empty();
    }
    /**
     * ¿Esta este usuario presente en el entorno?
     * @param id
     * @return true/false
     */
    public boolean isLocal(Long id){
        return Local.findById(id).isPresent();
    }
    
    public void saveSesionUsuario(Long id,String token,String estado){
        Local.insertar(id, token, estado);
    }
    public void updateSesionUsuario(Long id,String token,String estado){
        Local.actualizar(id, token, estado);
    }

    ResponseEntity deleteUsuario(Long user_id) {
        if(this.servicioAgente.deleteAgentesByUser(user_id)){
            Local.deleteSesionUserByUserId(user_id);
            this.Local.deleteUserById(user_id);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.internalServerError().build();
        }
    }
}
