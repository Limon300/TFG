/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.controllers;


import com.example.entorno.dto.POIRequest;
import com.example.entorno.dto.PoiResponse;
import com.example.entorno.service.POIService;
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
public class POIController {
    
    @Autowired
    SecurityService servicioSeguridad;
    
    @Autowired
    POIService servicioHabilidad;

    @PostMapping("/poi")
    public ResponseEntity<Long> add_habilidad(@RequestBody POIRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioHabilidad.addToStore(peticion).map(
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
    
    @GetMapping("/poi/{id}")
    public ResponseEntity<PoiResponse> get_habilidad(@PathVariable("id") Long sessionId,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicioHabilidad.getDataFromStore(sessionId).map(
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
    
    @PutMapping("/poi/{id}")
    public ResponseEntity alter_habilidad(@PathVariable("id") Long Id,@RequestBody POIRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioHabilidad.alterDataFromStore(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @DeleteMapping("/poi/{id}")
    public ResponseEntity delete_habilidad(@PathVariable("id") Long Id,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioHabilidad.deleteDataFromStore(Id);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @GetMapping("/poi")
    public ResponseEntity<List<PoiResponse>> get_habilidades(@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicioHabilidad.getAllDataFromStore().map(
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
    
}
