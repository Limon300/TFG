/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.service;


import com.example.store.dto.ObjetivoRequest;
import com.example.store.dto.ObjetivoResponse;
import com.example.store.models.ObjetivoModelo;
import com.example.store.repositories.ObjetivoRepository;
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
public class ObjetivoService {
    
    @Autowired
    ObjetivoRepository repositorio;
    
    public Optional<ObjetivoResponse> getData(Long Id) {
        Optional<ObjetivoModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new ObjetivoResponse(dato.get()));
        }
    }

    public Optional<Long> addData(ObjetivoRequest peticion) {
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(Long.valueOf(nuevo_id), peticion.getNombre(), peticion.getValor(), peticion.getDescripcion(),peticion.getCategoria());
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, ObjetivoRequest peticion) {
        if(repositorio.existsById(Id)){
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getValor()!= null) repositorio.updateValor(Id, peticion.getValor());
            if(peticion.getDescripcion() != null) repositorio.updateDescripcion(Id, peticion.getDescripcion());
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
    
    public List<ObjetivoResponse> getAllData() {
        List<ObjetivoModelo> aux = repositorio.findAll();
        List<ObjetivoResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new ObjetivoResponse(aux.get(i)));
        }
        return salida;
    }
    
}
