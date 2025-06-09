/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.store.service;

import com.example.store.dto.SensorRequest;
import com.example.store.dto.SensorResponse;
import com.example.store.models.SensorModelo;
import com.example.store.repositories.SensorRepository;
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
public class SensorService {
    
    @Autowired
    SensorRepository repositorio;

    public Optional<SensorResponse> getData(Long Id) {
        Optional<SensorModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new SensorResponse(dato.get()));
        }
    }

    public Optional<Long> addData(SensorRequest peticion) {
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(Long.valueOf(nuevo_id), peticion.getNombre(), peticion.getDescripcion(), peticion.getRadio(),peticion.getCategoria());
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, SensorRequest peticion) {
        if(repositorio.existsById(Id)){
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getDescripcion() != null) repositorio.updateDescripcion(Id, peticion.getDescripcion());
            if(peticion.getRadio() != null) repositorio.updateRadio(Id, peticion.getRadio());
            if(peticion.getCategoria()!= null) repositorio.updateCategoria(Id, peticion.getCategoria());
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
    
    public List<SensorResponse> getAllData() {
        List<SensorModelo> aux = repositorio.findAll();
        List<SensorResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new SensorResponse(aux.get(i)));
        }
        return salida;
    }
    
}
