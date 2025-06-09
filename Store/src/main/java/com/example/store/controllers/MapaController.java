/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.store.controllers;



import com.example.store.dto.Casilla;
import com.example.store.dto.CasillaRequest;
import com.example.store.dto.MapaRequest;
import com.example.store.dto.MapaResponse;
import com.example.store.service.MapaService;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/api")
public class MapaController {
    
    @Autowired
    MapaService servicio;
    
    @GetMapping("/mapas/{id}")//V
    public ResponseEntity<MapaResponse> get_mapa(@PathVariable("id") Long Id) {
        try {
            return servicio.getData(Id).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    //quizas se podrian juntar y expandir un poco 
    @GetMapping("/mapas/{id}/casilla")//V
    public ResponseEntity<Casilla> get_casilla(@PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y) 
    {
        try {
            return servicio.getDataCasilla(Id,Long.decode(X),Long.decode(Y)).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (NumberFormatException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/mapas/{id}/casillas")//V
    public ResponseEntity<List<Casilla>> get_casillas(@PathVariable("id") Long Id)
    {
        try {
            return servicio.getDataCasillas(Id).map(
                    ResponseEntity::ok
            ).orElse(
                    ResponseEntity.notFound().build()
            );
        } catch (NumberFormatException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/mapas") //V
    public ResponseEntity<Long> add_mapa(@RequestBody MapaRequest peticion){
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

    @PutMapping("/mapas/{id}") //V
    public ResponseEntity alter_mapa(@PathVariable("id") Long Id,@RequestBody MapaRequest peticion){
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
    
    @PutMapping("/mapas/{id}/casilla") //v
    public ResponseEntity alter_casilla(@PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y,
            @RequestBody CasillaRequest peticion) 
    {
        try {
            if(servicio.alterDataCasilla(Id,Long.decode(X),Long.decode(Y),peticion)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.notFound().build();              
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/mapas/{id}") //V
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
    
    @DeleteMapping("/mapas/{id}/casilla")//V
    public ResponseEntity delete_casilla(@PathVariable("id") Long Id,
            @RequestParam(required = true) String X,
            @RequestParam(required = true) String Y,
            @RequestBody CasillaRequest peticion) 
    {
        try {
            servicio.deleteObjetivos(Id,Long.decode(X),Long.decode(Y),peticion.getObjetivos());
            servicio.deletePOIs(Id,Long.decode(X),Long.decode(Y),peticion.getPOIs());
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/mapas") //V
    public ResponseEntity<List<MapaResponse>> get_agenteS() {
        try {
            return ResponseEntity.ok().body(servicio.getAllData());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }
    
}
