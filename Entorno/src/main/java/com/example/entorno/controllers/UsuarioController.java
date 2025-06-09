/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.controllers;

import com.example.entorno.dto.UsuarioDataResponse;
import com.example.entorno.dto.UsuarioPostRequest;
import com.example.entorno.service.SecurityService;
import com.example.entorno.service.UsuarioService;
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
public class UsuarioController {
    
    @Autowired
    SecurityService servicioSeguridad;
    
    @Autowired
    UsuarioService servicioUsuario;

    @PostMapping("/usuarios")
    public ResponseEntity<Long> add_usuario(@RequestBody UsuarioPostRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        peticion.setPassword(this.servicioSeguridad.Password_Hash(peticion.getPassword()));
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioUsuario.addToStore(peticion).map(
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
    
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDataResponse> get_usuario(@PathVariable("id") Long sessionId,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioUsuario.getDataFromStore(sessionId).map(
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
    
    @PutMapping("/usuarios/{id}")
    public ResponseEntity alter_usuario(@PathVariable("id") Long Id,@RequestBody UsuarioPostRequest peticion,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        peticion.setPassword(this.servicioSeguridad.Password_Hash(peticion.getPassword()));
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioUsuario.alterDataFromStore(Id,peticion);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity delete_usuario(@PathVariable("id") Long Id,@RequestHeader(HttpHeaders.AUTHORIZATION) final String language){
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioUsuario.deleteDataFromStore(Id);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        } 
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDataResponse>> get_usuarios(@RequestHeader(HttpHeaders.AUTHORIZATION) final String language) {
        if(this.servicioSeguridad.isTokenValidAdmin(language)) {
            try {
                return servicioUsuario.getAllDataFromStore().map(
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
