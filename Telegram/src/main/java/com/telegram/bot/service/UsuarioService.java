/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.telegram.bot.service;

import com.telegram.bot.dto.Registro;
import com.telegram.bot.dto.UsuarioDataResponse;
import com.telegram.bot.dto.UsuarioPostRequest;
import com.telegram.bot.dto.UsuarioRequest;
import com.telegram.bot.dto.UsuarioResponse;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
public class UsuarioService {
    @Value("${store}")
    private String store_adress;
    
    @Autowired
    RestTemplate restTemplate;
    
    public Optional<UsuarioDataResponse> getFromStore(Long id){
        String usrUrl = "http://"+store_adress+"/api/usuarios/" + id + "/data";
        try{
            ResponseEntity<UsuarioDataResponse> agentResponse = restTemplate.getForEntity(usrUrl, UsuarioDataResponse.class);
            if(agentResponse.getStatusCode().is2xxSuccessful()){
                return Optional.of(agentResponse.getBody());
            }
            else{
                return Optional.empty();
            }
        }
        catch(Exception e){
            return Optional.empty();
        }
    }
    
    public ResponseEntity alterDataFromStore(Long id,UsuarioPostRequest peticion){
        String usrUrl = "http://"+store_adress+"/api/usuarios/" + id;
        try{
            restTemplate.put(usrUrl, peticion);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    
    
    
}
