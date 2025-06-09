/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.store.controllers;


import com.example.store.dto.AgenteRequest;
import com.example.store.dto.AgenteResponse;
import com.example.store.service.AgenteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/api")
public class AgenteController {
    
    @Autowired
    AgenteService servicio;
    
    @GetMapping("/agentes/{id}")
    public ResponseEntity<AgenteResponse> get_agente(@PathVariable("id") Long sessionId) {
        try {
            return servicio.getData(sessionId).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/agentes")
    public ResponseEntity<Long> add_agente(@RequestBody AgenteRequest peticion){
        try {
            return servicio.addData(peticion).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/agentes/{id}")
    public ResponseEntity alter_agente(@PathVariable("id") Long Id,@RequestBody AgenteRequest peticion){
        try {
            if(servicio.alterData(Id,peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/agentes/{id}/sensores")
    public ResponseEntity add_sensor_agente(@PathVariable("id") Long Id,@RequestBody AgenteRequest peticion){
        try {
            if(servicio.addSensor(Id,peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/agentes/{id}/habilidades")
    public ResponseEntity add_habilidad_agente(@PathVariable("id") Long Id,@RequestBody AgenteRequest peticion){
        try {
            if(servicio.addHabilidad(Id,peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/agentes/{id}")
    public ResponseEntity delete_agente(@PathVariable("id") Long Id){
        try {
            if(servicio.deleteData(Id)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();             
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/agentes/{id}/sensores")
    public ResponseEntity delete_sensor_agente(@PathVariable("id") Long Id,@RequestBody AgenteRequest peticion){
        try {
            if(servicio.removeSensor(Id,peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/agentes/{id}/habilidades")
    public ResponseEntity delete_habilidad_agente(@PathVariable("id") Long Id,@RequestBody AgenteRequest peticion){
        try {
            if(servicio.removeHabilidad(Id,peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/agentes")
    public ResponseEntity<List<AgenteResponse>> get_agenteS() {
        try {
            return ResponseEntity.ok().body(servicio.getAllData());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }

}
