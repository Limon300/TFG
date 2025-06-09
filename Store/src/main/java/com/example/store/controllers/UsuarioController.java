/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.controllers;

import com.example.store.dto.UsuarioDataResponse;
import com.example.store.dto.UsuarioPostRequest;
import com.example.store.dto.UsuarioResponse;
import com.example.store.service.UsuarioService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author usuario
 */

@RestController
@RequestMapping("/api")
public class UsuarioController {
    
    @Autowired
    UsuarioService servicio;
    
    
    @GetMapping("/usuarios/{id}/login")
    public ResponseEntity<UsuarioResponse> get_login_info(@PathVariable("id") Long sessionId) {
        try {
            return servicio.get(sessionId).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/usuarios/{id}/data")
    public ResponseEntity<UsuarioDataResponse> get_general_info(@PathVariable("id") Long sessionId) {
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
    
    

    @PostMapping("/usuarios")
    public ResponseEntity<Long> add_usuario(@RequestBody UsuarioPostRequest peticion){
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

    @PutMapping("/usuarios/{id}")
    public ResponseEntity alter_usuario(@PathVariable("id") Long Id,@RequestBody UsuarioPostRequest peticion){
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
    
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity delete_usuario(@PathVariable("id") Long Id){
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
    
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDataResponse>> get_usuarios() {
        try {
            return ResponseEntity.ok().body(servicio.getAllData());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }
}
