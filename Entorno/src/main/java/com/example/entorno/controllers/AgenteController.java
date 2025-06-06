/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.controllers;



import com.example.entorno.dto.AgenteStoreRequest;
import com.example.entorno.dto.AgenteStoreResponse;
import com.example.entorno.service.AgenteService;
import com.example.entorno.service.SecurityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author usuario
 */

@RestController
@RequestMapping("/api")
public class AgenteController {
    
    @Autowired
    SecurityService servicioSeguridad;
    
    @Autowired
    AgenteService servicioAgente;

    @PostMapping("/agentes")
    public ResponseEntity<Long> add_agente(@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.addToStore(peticion).map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }     
    }
    
    @GetMapping("/agentes/{id}")
    public ResponseEntity<AgenteStoreResponse> get_agente(@PathVariable("id") Long sessionId,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicioAgente.getDataFromStore(sessionId).map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }     
    }
    
    @PutMapping("/agentes/{id}")
    public ResponseEntity alter_agente(@PathVariable("id") Long Id,@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.alterDataFromStore(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @DeleteMapping("/agentes/{id}")
    public ResponseEntity delete_agente(@PathVariable("id") Long Id,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.deleteDataFromStore(Id);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @GetMapping("/agentes")
    public ResponseEntity<List<AgenteStoreResponse>> get_agentes(@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicioAgente.getAllDataFromStore().map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }   
    }
    
    @PutMapping("/agentes/{id}/sensores")
    public ResponseEntity add_sensor_agente(@PathVariable("id") Long Id,@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.addSensor(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/agentes/{id}/habilidades")
    public ResponseEntity add_habilidad_agente(@PathVariable("id") Long Id,@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.addHabilidad(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/agentes/{id}/sensores")
    public ResponseEntity delete_sensor_agente(@PathVariable("id") Long Id,@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.removeSensor(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/agentes/{id}/habilidades")
    public ResponseEntity delete_habilidad_agente(@PathVariable("id") Long Id,@RequestBody AgenteStoreRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioAgente.removeHabilidad(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
}
