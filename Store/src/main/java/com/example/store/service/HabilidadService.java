/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.store.service;

import com.example.store.dto.HabilidadRequest;
import com.example.store.dto.HabilidadResponse;
import com.example.store.models.HabilidadModelo;
import com.example.store.repositories.HabilidadRepository;
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
public class HabilidadService {
    
    @Autowired
    HabilidadRepository repositorio;
    
    

    public Optional<HabilidadResponse> getData(Long Id) {
        Optional<HabilidadModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new HabilidadResponse(dato.get()));
        }
    }

    public Optional<Long> addData(HabilidadRequest peticion) {
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(
                Long.valueOf(nuevo_id),
                peticion.getNombre(),
                peticion.getV_up(),
                peticion.getV_down(),
                peticion.getDescripcion(),
                peticion.getCategoria()
        );
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, HabilidadRequest peticion) {
        if(repositorio.existsById(Id)){
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getV_down() != null) repositorio.updateV_down(Id, peticion.getV_down());
            if(peticion.getCategoria() != null) repositorio.updateCategoria(Id, peticion.getCategoria());
            if(peticion.getV_up() != null) repositorio.updateV_up(Id, peticion.getV_up());
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
    
    public List<HabilidadResponse> getAllData() {
        List<HabilidadModelo> aux = repositorio.findAll();
        List<HabilidadResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new HabilidadResponse(aux.get(i)));
        }
        return salida;
    }
    
}
