/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.store.controllers;

import com.example.store.dto.ObjetivoRequest;
import com.example.store.dto.ObjetivoResponse;
import com.example.store.service.ObjetivoService;
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
public class ObjetivoController {
    
    @Autowired
    ObjetivoService servicio;
    
    @GetMapping("/objetivos/{id}")
    public ResponseEntity<ObjetivoResponse> get_objetivo(@PathVariable("id") Long sessionId) {
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

    @PostMapping("/objetivos")
    public ResponseEntity<Long> add_objetivo(@RequestBody ObjetivoRequest peticion){
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

    @PutMapping("/objetivos/{id}")
    public ResponseEntity alter_objetivo(@PathVariable("id") Long Id,@RequestBody ObjetivoRequest peticion){
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
    
    @DeleteMapping("/objetivos/{id}")
    public ResponseEntity delete_objetivo(@PathVariable("id") Long Id){
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
    
    @GetMapping("/objetivos")
    public ResponseEntity<List<ObjetivoResponse>> get_objetivos() {
        try {
            return ResponseEntity.ok().body(servicio.getAllData());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }
}
