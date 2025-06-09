/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.service;


import com.example.store.dto.POIRequest;
import com.example.store.dto.PoiResponse;
import com.example.store.models.POIModelo;
import com.example.store.repositories.POIRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class POIService {
    
    @Autowired
    POIRepository repositorio;
    
    public Optional<PoiResponse> getData(Long Id) {
        Optional<POIModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new PoiResponse(dato.get()));
        }
    }

    public Optional<Long> addData(POIRequest peticion) {
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(Long.valueOf(nuevo_id), peticion.getNombre(), peticion.getValor(), peticion.getDescripcion(),peticion.getCategoria());
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, POIRequest peticion) {
        if(repositorio.existsById(Id)){
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getValor()!= null) repositorio.updateValor(Id, peticion.getValor());
            if(peticion.getDescripcion() != null) repositorio.updateDescripcion(Id, peticion.getDescripcion());
            return true;
        }
        else{
            return false;  
        }
    }

    public boolean deleteData(Long Id) {
        if(repositorio.existsById(Id)){
            repositorio.deleteById(Id);
            return true;
        }
        else{
            return false;  
        }
    }
    
    public List<PoiResponse> getAllData() {
        List<POIModelo> aux = repositorio.findAll();
        List<PoiResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new PoiResponse(aux.get(i)));
        }
        return salida;
    }
    
}
