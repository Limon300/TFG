/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.store.controllers;

import com.example.store.dto.HabilidadRequest;
import com.example.store.dto.HabilidadResponse;
import com.example.store.service.HabilidadService;
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
public class HabilidadController {
    
    @Autowired
    HabilidadService servicio;
    
    @GetMapping("/habilidades/{id}")
    public ResponseEntity<HabilidadResponse> get_habilidad(@PathVariable("id") Long sessionId) {
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

    @PostMapping("/habilidades")
    public ResponseEntity<Long> add_habilidad(@RequestBody HabilidadRequest peticion){
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

    @PutMapping("/habilidades/{id}")
    public ResponseEntity alter_habilidad(@PathVariable("id") Long Id,@RequestBody HabilidadRequest peticion){
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
    
    @DeleteMapping("/habilidades/{id}")
    public ResponseEntity delete_habilidad(@PathVariable("id") Long Id){
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
    
    @GetMapping("/habilidades")
    public ResponseEntity<List<HabilidadResponse>> get_habilidades() {
        try {
            return ResponseEntity.ok().body(servicio.getAllData());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }
}
