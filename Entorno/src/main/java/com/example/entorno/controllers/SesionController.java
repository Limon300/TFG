/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.entorno.controllers;

import com.example.entorno.dto.ActionRequest;
import com.example.entorno.dto.ActionResponse;
import com.example.entorno.dto.LogRequest;
import com.example.entorno.dto.LogResponse;
import com.example.entorno.dto.ObjetivoSesionResponse;
import com.example.entorno.dto.SensorRequest;
import com.example.entorno.dto.SensorResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.dto.SesionPostRequest;
import com.example.entorno.dto.SesionStart;
import com.example.entorno.dto.SesionUserRequest;
import com.example.entorno.dto.SesionUserStart;
import com.example.entorno.service.SecurityService;
import com.example.entorno.service.SesionService;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/api")
public class SesionController {
    @Autowired
    SecurityService servicioSeguridad;
    
    @Autowired
    SesionService servicioSesion;
    
    @PostMapping("/sesion")
    public ResponseEntity<SesionStart> crear_sesion(
            @RequestBody SesionPostRequest peticion,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String token)
    {
        if(this.servicioSeguridad.isTokenValidOpenAccess(token)) {
            try {
                return servicioSesion.inicio(peticion,Long.decode(this.servicioSeguridad.extractId(token))).map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (NumberFormatException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/sesion/{id}/usuario")
    public ResponseEntity<SesionUserStart> invitar_usuario(
        @RequestBody SesionUserRequest peticion,
        @PathVariable("id") Long sesionId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    ){
        if(this.servicioSeguridad.isTokenValidSesion(token, sesionId)){
           try {
                return servicioSesion.invitar_usuario(sesionId,peticion).map(
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
    
    @PostMapping("/sesion/{id}/habilidad")
    public ResponseEntity<ActionResponse> usar_habilidad(
        @PathVariable("id") Long sessionId,
        @RequestBody ActionRequest peticion,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    ){
        if(this.servicioSeguridad.isTokenValidSesion(token, sessionId)){
           try {
                return servicioSesion.habilidad(sessionId,peticion,Long.decode(this.servicioSeguridad.extractId(token))).map(
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
    //Voy a necesitar una lista de las sesiones del solicitante
    @GetMapping("/sesion")
    public ResponseEntity<List<SesionData>> consultar_sesiones(
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidOpenAccess(token)) {
            try {
                return ResponseEntity.ok(servicioSesion.consultarUsuario(this.servicioSeguridad.extractId(token)));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/sesion/{id}")
    public ResponseEntity<SesionData> consultar_sesion(
        @PathVariable("id") Long sessionId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidSesion(token,sessionId) || this.servicioSeguridad.isTokenValidAdmin(token)) {
            try {
                return servicioSesion.consultar(sessionId).map(
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
    
    @GetMapping("/sesion/{id}/logs/{fecha}")
    public ResponseEntity<List<LogResponse>> consultar_logs(
        @PathVariable("id") Long sessionId,
        @PathVariable("fecha") Long fecha,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidSesion(token,sessionId)) {
            LogRequest peticion = new LogRequest();
            peticion.setFecha_max(null);
            peticion.setFecha_min(new Timestamp(fecha));
            try {
                return ResponseEntity.ok(servicioSesion.consultarLogs(sessionId,peticion));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/sesion/{id}/logs")
    public ResponseEntity<List<LogResponse>> consultar_logs(
        @PathVariable("id") Long sessionId,
        @RequestBody LogRequest peticion,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidSesion(token,sessionId)) {
            try {
                return ResponseEntity.ok(servicioSesion.consultarLogs(sessionId,peticion));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/sesion/{id}/objetivos")
    public ResponseEntity<List<ObjetivoSesionResponse>> consultar_objetivos(
        @PathVariable("id") Long sessionId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidSesion(token,sessionId)) {
            try {
                return ResponseEntity.ok(servicioSesion.consultarObjetivos(sessionId));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/sesion/{id}/objetivos/actual")
    public ResponseEntity<List<ObjetivoSesionResponse>> consultar_objetivos_actual(
        @PathVariable("id") Long sessionId,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    )
    {
        if(this.servicioSeguridad.isTokenValidSesion(token,sessionId)) {
            try {
                return ResponseEntity.ok(servicioSesion.consultarObjetivos(sessionId));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    /*
    @GetMapping("/sesion/{id}/sensor")
    public ResponseEntity<SensorResponse> usar_sensor(
        @PathVariable("id") Long sessionId,
        @RequestBody SensorRequest peticion,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    ){
        if(this.servicioSeguridad.isTokenValidSesion(token, sessionId)){
           try {
                return servicioSesion.sensor(sessionId,peticion,Long.decode(this.servicioSeguridad.extractId(token))).map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (NumberFormatException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    */
    @GetMapping("/sesion/{id}/sensor")
    public ResponseEntity<SensorResponse> usar_sensor(
        @PathVariable("id") Long sessionId,
        @RequestParam(required = true) Long id_agente,
        @RequestParam(required = true) Long id_sensor,
        @RequestHeader(HttpHeaders.AUTHORIZATION) final String token
    ){
        if(this.servicioSeguridad.isTokenValidSesion(token, sessionId)){
           try {
                return servicioSesion.sensor(sessionId,new SensorRequest(id_agente,id_sensor),Long.decode(this.servicioSeguridad.extractId(token))).map(
                        ResponseEntity::ok
                ).orElse(
                        ResponseEntity.notFound().build()
                );
            } catch (NumberFormatException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/sesion/{id}/{usr}")
    public ResponseEntity abandonar_sesion(
            @PathVariable("id") Long sessionId,
            @PathVariable("usr") Long userId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language)
    {
        if(this.servicioSeguridad.isTokenValidRestricted(language,userId)) {
            try {
                return servicioSesion.salir(sessionId,userId);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/sesion/{id}")
    public ResponseEntity eliminar_sesion(
            @PathVariable("id") Long sessionId,
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String language)
    {
        if(this.servicioSeguridad.isTokenValidSesion(language,sessionId)) {
            try {
                return servicioSesion.fin(sessionId);
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    
}
