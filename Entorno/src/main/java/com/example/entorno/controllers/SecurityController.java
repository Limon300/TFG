/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.entorno.controllers;

import com.example.entorno.dto.ActionRequest;
import com.example.entorno.dto.LoginRequest;
import com.example.entorno.dto.TokenResponse;
import com.example.entorno.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/api")
public class SecurityController {
    
    @Autowired
    SecurityService servicioSeguridad;
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest peticion) {        
        try {
            return servicioSeguridad.login(peticion).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/logout/{id}")
    public ResponseEntity logout(
        @PathVariable("id") Long user_id,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    ){        
        if(this.servicioSeguridad.isTokenValidRestricted(token, user_id)){
            try {
                return servicioSeguridad.logout(user_id);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
}
