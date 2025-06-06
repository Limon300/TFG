/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.controllers;


import com.example.entorno.dto.MapaRequest;
import com.example.entorno.dto.MapaResponse;
import com.example.entorno.dto.CasillaRequest;
import com.example.entorno.dto.Casilla;
import com.example.entorno.service.MapaService;
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
public class MapaController {
    
    @Autowired
    SecurityService servicioSeguridad;
    
    @Autowired
    MapaService servicio;

    @PostMapping("/mapas")
    public ResponseEntity<Long> add_mapa(@RequestBody MapaRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicio.addToStore(peticion).map(
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
    
    @GetMapping("/mapas/{id}")
    public ResponseEntity<MapaResponse> get_mapa(@PathVariable("id") Long sessionId,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicio.getDataFromStore(sessionId).map(
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
    
    @GetMapping("/mapas/{id}/casilla")
    public ResponseEntity<Casilla> get_casilla(@PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicio.getCasillaFromStore(Id,Long.decode(X),Long.decode(Y)).map(
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
    
    @GetMapping("/mapas/{id}/casillas")
    public ResponseEntity<List<Casilla>> get_casillas(@PathVariable("id") Long Id,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicio.getCasillasFromStore(Id).map(
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
    
    @PutMapping("/mapas/{id}")
    public ResponseEntity alter_mapa(@PathVariable("id") Long Id,@RequestBody MapaRequest peticion,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicio.alterDataFromStore(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @PutMapping("/mapas/{id}/casilla")
    public ResponseEntity alter_casilla(@PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y,
            @RequestBody CasillaRequest peticion,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicio.alterDataCasilla(Id,Long.decode(X),Long.decode(Y),peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
        
    }
    
    @DeleteMapping("/mapas/{id}")
    public ResponseEntity delete_mapa(
            @PathVariable("id") Long Id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language
    ){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicio.deleteDataFromStore(Id);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @DeleteMapping("/mapas/{id}/casilla")
    public ResponseEntity delete_casilla(
            @PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y,
            @RequestBody CasillaRequest peticion,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language
    ) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicio.deleteInCasillaFromStore(Id,Long.decode(X),Long.decode(Y),peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @GetMapping("/mapas")
    public ResponseEntity<List<MapaResponse>> get_habilidades(@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidOpenAccess(language)) {
            try {
                return servicio.getAllDataFromStore().map(
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
